import React from 'react';
import { render } from '@testing-library/react';
import { setLink } from './setLink';
import { places } from '../constants/places';

const card = { linkToMeeting: 'https://www.google.com/', name: 'Google' };
describe('setLink function', () => {
    it('should return LinkToMeeting component if places.TOGETHER', () => {
        const result = setLink(card, places.TOGETHER);
        const { container } = render(result);
        expect(container.innerHTML).not.toBe('');
    });

    it('should return link with href if places.ONLINE', () => {
        const card = { linkToMeeting: 'https://www.google.com/', name: 'Google' };

        const result = setLink(card, places.ONLINE);
        const { container } = render(<>{result}</>);

        expect(container.querySelector('a')).not.toBeNull();
    });
    it('should return null if places = null', () => {
        expect(setLink(card)).toBeNull();
    });
});
