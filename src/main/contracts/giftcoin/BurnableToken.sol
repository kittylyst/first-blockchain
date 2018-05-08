pragma solidity 0.4.19;

import "zeppelin-solidity/contracts/token/ERC20/StandardToken.sol";
import "zeppelin-solidity/contracts/ownership/Ownable.sol";


/**
* @title Customized Burnable Token
* @dev Token that can be irreversibly burned (destroyed).
*/
contract BurnableToken is StandardToken, Ownable {

    event Burn(address indexed burner, uint256 amount);

    /**
    * @dev Anybody can burn a specific amount of their tokens.
    * @param _amount The amount of token to be burned.
    */
    function burn(uint256 _amount) public {
        burnInternal(msg.sender, _amount);
    }

    /**
    * @dev Owner can burn a specific amount of tokens of other token holders.
    * @param _from The address of token holder whose tokens to be burned.
    * @param _amount The amount of token to be burned.
    */
    function burnFrom(address _from, uint256 _amount) public onlyOwner {
        burnInternal(_from, _amount);
    }

    /**
    * @dev Burns a specific amount of tokens of a token holder.
    * @param _from The address of token holder whose tokens are to be burned.
    * @param _amount The amount of token to be burned.
    */
    function burnInternal(address _from, uint256 _amount) internal {
        require(_from != address(0));
        require(_amount > 0);
        require(_amount <= balances[_from]);
        // no need to require _amount <= totalSupply, since that would imply the
        // sender's balance is greater than the totalSupply, which *should* be an assertion failure

        balances[_from] = balances[_from].sub(_amount);
        totalSupply_ = totalSupply_.sub(_amount);
        Transfer(_from, address(0), _amount);
        Burn(_from, _amount);
    }

}
