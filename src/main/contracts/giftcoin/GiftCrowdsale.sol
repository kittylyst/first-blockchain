pragma solidity 0.4.19;

import "zeppelin-solidity/contracts/lifecycle/Pausable.sol";
import "zeppelin-solidity/contracts/ownership/Ownable.sol";

import "./GiftToken.sol";
import "./Whitelist.sol";


contract GiftCrowdsale is Pausable {
    using SafeMath for uint256;

    uint256 public startTimestamp = 0;

    uint256 public endTimestamp = 0;

    uint256 public exchangeRate = 0;

    uint256 public tokensSold = 0;

    uint256 constant public minimumInvestment = 25e16; // 0.25 ETH

    uint256 public minCap = 0;

    uint256 public endFirstPeriodTimestamp = 0;
    uint256 public endSecondPeriodTimestamp = 0;
    uint256 public endThirdPeriodTimestamp = 0;

    GiftToken public token;
    Whitelist public whitelist;

    mapping(address => uint256) public investments;

    modifier beforeSaleOpens() {
        require(now < startTimestamp);
        _;
    }

    modifier whenSaleIsOpen() {
        require(now >= startTimestamp && now < endTimestamp);
        _;
    }

    modifier whenSaleHasEnded() {
        require(now >= endTimestamp);
        _;
    }

    /**
    * @dev Constructor for GiftCrowdsale contract.
    * @dev Set first owner who can manage whitelist.
    * @param _startTimestamp uint256 The start time ico.
    * @param _endTimestamp uint256 The end time ico.
    * @param _exchangeRate uint256 The price of the Gift token.
    * @param _minCap The minimum amount of tokens sold required for the ICO to be considered successful.
    */
    function GiftCrowdsale (
        uint256 _startTimestamp,
        uint256 _endTimestamp,
        uint256 _exchangeRate,
        uint256 _minCap
    )
    public
    {
        require(_startTimestamp >= now && _endTimestamp > _startTimestamp);
        require(_exchangeRate > 0);

        startTimestamp = _startTimestamp;
        endTimestamp = _endTimestamp;

        exchangeRate = _exchangeRate;

        endFirstPeriodTimestamp = _startTimestamp.add(1 days);
        endSecondPeriodTimestamp = _startTimestamp.add(1 weeks);
        endThirdPeriodTimestamp = _startTimestamp.add(2 weeks);

        minCap = _minCap;

        pause();
    }

    function discount() public view returns (uint256) {
        if (now > endThirdPeriodTimestamp)
            return 0;
        if (now > endSecondPeriodTimestamp)
            return 5;
        if (now > endFirstPeriodTimestamp)
            return 15;
        return 25;
    }

    function bonus(address _wallet) public view returns (uint256) {
        uint256 _created = whitelist.walletCreatedTimestamp(_wallet);
        if (_created > 0 && _created < startTimestamp) {
            return 10;
        }
        return 0;
    }

    /**
    * @dev Function for sell tokens.
    * @dev Sells tokens only for wallets from Whitelist while ICO lasts
    */
    function sellTokens() public payable whenSaleIsOpen whenWhitelisted(msg.sender) whenNotPaused {
        require(msg.value > minimumInvestment);
        uint256 _bonus = bonus(msg.sender);
        uint256 _discount = discount();
        uint256 tokensAmount = (msg.value).mul(exchangeRate).mul(_bonus.add(100)).div((100 - _discount));

        token.transferFromIco(msg.sender, tokensAmount);

        tokensSold = tokensSold.add(tokensAmount);

        addInvestment(msg.sender, msg.value);
    }

    /**
    * @dev Fallback function allowing the contract to receive funds
    */
    function() public payable {
        sellTokens();
    }

    /**
    * @dev Function for funds withdrawal
    * @dev transfers funds to specified wallet once ICO is ended
    * @param _wallet address wallet address, to  which funds  will be transferred
    */
    function withdrawal(address _wallet) external onlyOwner whenSaleHasEnded {
        require(_wallet != address(0));
        _wallet.transfer(this.balance);

        token.transferOwnership(msg.sender);
    }

    /**
    * @dev Function for manual token assignment (token transfer from ICO to requested wallet)
    * @param _to address The address which you want transfer to
    * @param _value uint256 the amount of tokens to be transferred
    */
    function assignTokens(address _to, uint256 _value) external onlyOwner {
        token.transferFromIco(_to, _value);

        tokensSold = tokensSold.add(_value);
    }

    /**
    * @dev Add new investment to the ICO investments storage.
    * @param _from The address of a ICO investor.
    * @param _value The investment received from a ICO investor.
    */
    function addInvestment(address _from, uint256 _value) internal {
        investments[_from] = investments[_from].add(_value);
    }

    /**
    * @dev Function to return money to one customer, if mincap has not been reached
    */
    function refundPayment() external whenWhitelisted(msg.sender) whenSaleHasEnded {
        require(tokensSold < minCap);
        require(investments[msg.sender] > 0);

        token.burnFrom(msg.sender, token.balanceOf(msg.sender));

        uint256 investment = investments[msg.sender];
        investments[msg.sender] = 0;
        (msg.sender).transfer(investment);
    }

    /**
    * @dev Allows the current owner to transfer control of the token contract from ICO to a newOwner.
    * @param _newOwner The address to transfer ownership to.
    */
    function transferTokenOwnership(address _newOwner) public onlyOwner {
        token.transferOwnership(_newOwner);
    }

    function updateIcoEnding(uint256 _endTimestamp) public onlyOwner {
        endTimestamp = _endTimestamp;
    }

    modifier whenWhitelisted(address _wallet) {
        require(whitelist.isWhitelisted(_wallet));
        _;
    }

    function init(address _token, address _whitelist) public onlyOwner {
        require(_token != address(0) && _whitelist != address(0));
        // function callable only once
        require(token == address(0) && whitelist == address(0));
        // required for refund purposes (token.burnFrom())
        require(Ownable(_token).owner() == address(this));

        token = GiftToken(_token);
        whitelist = Whitelist(_whitelist);

        unpause();
    }

    /**
    * @dev Owner can't unpause the crowdsale before calling init().
    */
    function unpause() public onlyOwner whenPaused {
        require(token != address(0) && whitelist != address(0));
        super.unpause();
    }

    /**
    * @dev Owner can change the exchange rate before ICO begins
    * @param _exchangeRate new exchange rate
    */
    function setExchangeRate(uint256 _exchangeRate) public onlyOwner beforeSaleOpens {
        require(_exchangeRate > 0);

        exchangeRate = _exchangeRate;
    }
}
