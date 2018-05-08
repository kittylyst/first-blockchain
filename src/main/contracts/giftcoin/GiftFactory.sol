pragma solidity 0.4.19;

import "./GiftCrowdsale.sol";
import "./Whitelist.sol";


contract GiftFactory {
    GiftCrowdsale public crowdsale;

    function createCrowdsale (
        uint256 _startTimestamp,
        uint256 _endTimestamp,
        uint256 _exchangeRate,
        uint256 _minCap
    )
        public
    {
        crowdsale = new GiftCrowdsale(
            _startTimestamp,
            _endTimestamp,
            _exchangeRate,
            _minCap
        );

        Whitelist whitelist = crowdsale.whitelist();

        crowdsale.transferOwnership(msg.sender);
        whitelist.transferOwnership(msg.sender);
    }
}
