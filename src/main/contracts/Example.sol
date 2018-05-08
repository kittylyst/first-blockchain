pragma solidity ^0.4.19;

contract Example 
{ 
    uint256 data; 
    address owner; 

    event logData(uint256 dataToLog);  

    modifier onlyOwner() { 
        if (msg.sender != owner) throw; 
        _; 
    } 

    // Java-like constructor syntax 
    // This code is run only when the contract is created.
    function Example(uint256 initData, address initOwner) public { 
        data = initData; 
        owner = initOwner; 
    } 

    // Accessors
    function getData() returns (uint256 out) public {
        return data; 
    } 

    function setData(uint256 newData) onlyOwner { 
        emit logData(newData); 
        data = newData; 
    } 
}
