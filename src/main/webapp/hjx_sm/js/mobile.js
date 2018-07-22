/**
 * usage mobile rem
 */

(function () {
    var PSD_WIDTH = 750; //.psd width
    function changeRem() {
        var width = document.documentElement.clientWidth;
        document.documentElement.style.fontSize = width * 100 / PSD_WIDTH + 'px';
    }
    changeRem();
    window.addEventListener('resize', function () {
        changeRem();
    })
})()

