import React from 'react';
import LinkToMeeting from '../components/LinkToMeeting/LinkToMeeting';
import { places } from '../constants/places';

export const setLink = (card, place) => {
    if (!card || !place) return null;

    const normalizedPlace = place.toLowerCase();

    if (normalizedPlace === places.TOGETHER) {
        return <LinkToMeeting {...card} />;
    }

    if (normalizedPlace === places.ONLINE && card?.linkToMeeting) {
        return (
            <a href={card.linkToMeeting}>
                {card.name || 'link'}
            </a>
        );
    }

    return null;
};