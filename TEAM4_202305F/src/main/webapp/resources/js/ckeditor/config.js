/**
 * @license Copyright (c) 2003-2023, CKSource Holding sp. z o.o. All rights reserved.
 * For licensing, see https://ckeditor.com/legal/ckeditor-oss-license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.extraPlugins = 'autogrow';
	config.autoGrow_onStartup = true;

	config.autoGrow_minHeight = 200;
	
	config.autoGrow_maxHeight = 1000;
	
	config.autoGrow_bottomSpace = 50;
};
