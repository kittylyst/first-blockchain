pragma solidity 0.4.19;

import "zeppelin-solidity/contracts/lifecycle/Pausable.sol";

import "./BurnableToken.sol";


contract GiftToken is BurnableToken, Pausable {

    string public name = "Giftcoin";
    string public symbol = "GIFT";
    uint8 public decimals = 18;
  
    uint256 public initialTotalSupply = uint256(1e8) * (uint256(10) ** decimals);

    address private addressIco;

    modifier onlyIco() {
        require(msg.sender == addressIco);
        _;
    }

    /**
    * @dev Create GiftToken contract and set pause
    * @param _ico The address of ICO contract.
    */
    function GiftToken(address _ico) public {
        pause();
        setIcoAddress(_ico);

        totalSupply_ = initialTotalSupply;
        balances[_ico] = balances[_ico].add(initialTotalSupply);
        Transfer(address(0), _ico, initialTotalSupply);
    }

    function setIcoAddress(address _ico) public onlyOwner {
        require(_ico != address(0));
        // to change the ICO address firstly transfer the tokens to the new ICO
        require(balanceOf(addressIco) == 0);

        addressIco = _ico;
  
        // the ownership of the token needs to be transferred to the crowdsale contract
        // but it can be reclaimed using transferTokenOwnership() function
        // or along withdrawal of the funds
        transferOwnership(_ico);
    }

    /**
    * @dev Transfer token for a specified address with pause feature for owner.
    * @dev Only applies when the transfer is allowed by the owner.
    * @param _to The address to transfer to.
    * @param _value The amount to be transferred.
    */
    function transfer(address _to, uint256 _value) public whenNotPaused returns (bool) {
        return super.transfer(_to, _value);
    }

    /**
    * @dev Transfer tokens from one address to another with pause feature for owner.
    * @dev Only applies when the transfer is allowed by the owner.
    * @param _from address The address which you want to send tokens from
    * @param _to address The address which you want to transfer to
    * @param _value uint256 the amount of tokens to be transferred
    */
    function transferFrom(address _from, address _to, uint256 _value) public whenNotPaused returns (bool) {
        return super.transferFrom(_from, _to, _value);
    }

    /**
    * @dev Transfer tokens from ICO address to another address.
    * @param _to The address to transfer to.
    * @param _value The amount to be transferred.
    */
    function transferFromIco(address _to, uint256 _value) public onlyIco returns (bool) {
        return super.transfer(_to, _value);
    }
}
