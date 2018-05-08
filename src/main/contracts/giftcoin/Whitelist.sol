pragma solidity 0.4.19;

import "zeppelin-solidity/contracts/ownership/Ownable.sol";


/**
 * @title Whitelist contract
 * @dev Whitelist for wallets, with additional data for every wallet.
*/
contract Whitelist is Ownable {
    struct WalletInfo {
        string data;
        bool whitelisted;
        uint256 createdTimestamp;
    }

    address public backendAddress;

    mapping(address => WalletInfo) public whitelist;

    uint256 public whitelistLength = 0;

    /**
    * @dev Sets the backend address for automated operations.
    * @param _backendAddress The backend address to allow.
    */
    function setBackendAddress(address _backendAddress) public onlyOwner {
        require(_backendAddress != address(0));
        backendAddress = _backendAddress;
    }

    /**
    * @dev Allows the function to be called only by the owner and backend.
    */
    modifier onlyPrivilegedAddresses() {
        require(msg.sender == owner || msg.sender == backendAddress);
        _;
    }

    /**
    * @dev Add wallet to whitelist.
    * @dev Accept request from privilege adresses only.
    * @param _wallet The address of wallet to add.
    * @param _data The checksum of additional wallet data.
    */  
    function addWallet(address _wallet, string _data) public onlyPrivilegedAddresses {
        require(_wallet != address(0));
        require(!isWhitelisted(_wallet));
        whitelist[_wallet].data = _data;
        whitelist[_wallet].whitelisted = true;
        whitelist[_wallet].createdTimestamp = now;
        whitelistLength++;
    }

    /**
    * @dev Update additional data for whitelisted wallet.
    * @dev Accept request from privilege adresses only.
    * @param _wallet The address of whitelisted wallet to update.
    * @param _data The checksum of new additional wallet data.
    */      
    function updateWallet(address _wallet, string _data) public onlyPrivilegedAddresses {
        require(_wallet != address(0));
        require(isWhitelisted(_wallet));
        whitelist[_wallet].data = _data;
    }

    /**
    * @dev Remove wallet from whitelist.
    * @dev Accept request from privilege adresses only.
    * @param _wallet The address of whitelisted wallet to remove.
    */  
    function removeWallet(address _wallet) public onlyPrivilegedAddresses {
        require(_wallet != address(0));
        require(isWhitelisted(_wallet));
        delete whitelist[_wallet];
        whitelistLength--;
    }

    /**
    * @dev Check the specified wallet whether it is in the whitelist.
    * @param _wallet The address of wallet to check.
    */ 
    function isWhitelisted(address _wallet) public view returns (bool) {
        return whitelist[_wallet].whitelisted;
    }

    /**
    * @dev Get the checksum of additional data for the specified whitelisted wallet.
    * @param _wallet The address of wallet to get.
    */ 
    function walletData(address _wallet) public view returns (string) {
        return whitelist[_wallet].data;
    }

    /**
    * @dev Get the creation timestamp for the specified whitelisted wallet.
    * @param _wallet The address of wallet to get.
    */
    function walletCreatedTimestamp(address _wallet) public view returns (uint256) {
        return whitelist[_wallet].createdTimestamp;
    }
}
