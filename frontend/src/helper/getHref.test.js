import getHref from './getHref';

describe('getHref - edge cases', () => {

    it('should return empty string when text is null', () => {
        expect(getHref(null, 'https://google.com')).toBe('');
    });

    it('should return empty string when link is null', () => {
        expect(getHref('Google', null)).toBe('');
    });

    it('should return empty string when both are null', () => {
        expect(getHref(null, null)).toBe('');
    });

    it('should handle whitespace only text', () => {
        expect(getHref('   ', 'https://google.com')).toBe('');
    });

    it('should handle whitespace only link', () => {
        expect(getHref('Google', '   ')).toBe('');
    });

    it('should correctly build link with trimming', () => {
        expect(getHref('  Google  ', '  https://google.com  '))
            .toBe('<a href="https://google.com">Google</a>');
    });
});