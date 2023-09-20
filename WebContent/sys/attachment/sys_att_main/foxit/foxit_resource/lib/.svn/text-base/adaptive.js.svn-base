var deviceInfo = (function () {
    var USER_AGENT = navigator.userAgent.toLowerCase();
    var isIOS = /ipad|iphone|ipod/.test(USER_AGENT);
    var isAndroid = /android/.test(USER_AGENT);
    var isBlackBerry = /blackberry/.test(USER_AGENT);
    var isWebOS = /webos/.test(USER_AGENT);
    var isKindle = /silk|kindle/.test(USER_AGENT);

    var isAliAppBrowser = /aliapp/.test(USER_AGENT);

    var isTablet = !isAliAppBrowser && /(ipad|tablet|(android(?!.*mobile))|(windows(?!.*phone)(.*touch))|kindle|playbook|silk|(puffin(?!.*(IP|AP|WP))))/.test(USER_AGENT);
    var isMobile = !isTablet && (/mobile/.test(USER_AGENT) || isIOS || isAndroid || isBlackBerry || isWebOS || isKindle);
    return {
        isTablet: isTablet,
        isMobile: isMobile
    };
})();
(function() {
    var html = document.documentElement;
    if (!deviceInfo.isMobile) {
        return;
    }
    var designDraftWidth = 750;

    function resetOnResize() {
        var size = Math.min(screen.availHeight, screen.availWidth);
        var rem = size / designDraftWidth * 100;
        html.style.cssText += 'font-size:' + rem + 'px';
    }

    window.addEventListener('pageshow', function(e) {
        if (e.persisted) {
            resetOnResize();
        }
    });
    var lastOrientation = window.orientation;
    window.addEventListener('resize', function () {
        if (lastOrientation !== window.orientation) {
            resetOnResize();
        }
        lastOrientation = window.orientation;
    });
    resetOnResize();
})();