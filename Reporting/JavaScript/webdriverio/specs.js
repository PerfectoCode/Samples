

describe('my webdriverio tests', function () {

    it('Navigate and search in google', function () {
        browser.reportingClient.testStep('Navigate and search in google');
        browser.url('http://www.google.com')
            .setValue('#lst-ib', 'PerfectoCode GitHub')
            .click('#tsbb')
            .click('#rso > div.g.kno-result._rk.mnr-c.g-blk > div > div > div._OKe > div:nth-child(2) > div > div > div > div.rc > div._OXf > h3 > a');
        // browser.reportingClient.testStep('Assert Keyword Perfecto is in title');
        var title = browser.getTitle();
        expect(title).toContain('Perfecto')
    });
});