(function () {
    function inputScript(url) {
        var script = '<script type="text/javascript" src="' + url + '"><' + '/script>';
        document.writeln(script);
    }

    function inputCSS(url) {
        var css = '<link rel="stylesheet" href="' + url + '">';
        document.writeln(css);
    }

    function supportES6() {
        var code = "'use strict'; class Foo {}; class Bar extends Foo {};";
        try {
            (new Function(code))();
        } catch (err) {
            return false;
        }
        if (!Array.from) {
            return false;
        }
        return true;
    }

    function load() {
        inputScript("/js/ol/ol.js");
        inputCSS("/css/ol/ol.css");
        inputCSS("/css/ol/iclient-openlayers.min.css");

        if (supportES6()) {
            inputScript("/js/ol/iclient-openlayers-es6.min.js");
        } else {
            inputScript("/js/ol/iclient-openlayers.min.js");
        }
    }

    load();
    window.isLocal = false;
    window.server = document.location.toString().match(/file:\/\//) ? "http://localhost:8090" : document.location.protocol + "//" + document.location.host;
})();