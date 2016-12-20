
describe('Perfecto Native NodeJS - Selendroid app', function () {

	it('Test Native and Webview', function () {
		// Native 
		browser
			.clearElement('//*[@resource-id="io.selendroid.testapp:id/my_text_field"]')
			.setValue('//*[@resource-id="io.selendroid.testapp:id/my_text_field"]', 'Guardians Of The Galaxy')
			.click('//*[@resource-id="io.selendroid.testapp:id/visibleButtonTest"]');

			// Assert seeing floating text
			let text = browser.getText('//*[@resource-id="io.selendroid.testapp:id/visibleTextView"]');
			expect(text).toContain('sometimes');

		// Play with WebView
		browser
			.click('//*[@resource-id="io.selendroid.testapp:id/buttonStartWebview"]')
			.clearElement('//*[@resource-id="name_input"]')
			.setValue('//*[@resource-id="name_input"]' , 'Groot')
			.click('//*[@content-desc="Volvo"]')
			.click('//*[@text="Mercedes"]')
			.click('//*[@content-desc="Send me your name!"]');
	});
});



