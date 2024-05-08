/*var vm = new Vue({
    el: '#app',
    data() {
        return {
            volumeValue: '',
        }
    },
    mounted() {
    },
    methods: {},

});*/

/*jquery*/
$(function () {
    var volumeIcon = $('.volume');
    var volume_slider = $('.volume_slider'); // 音量隐藏部分 dom元素

    // 鼠标悬浮时显示/隐藏音量条
    volumeIcon.hover(() => { volume_slider.css({"display":"block"}) }, hide);
    // 鼠标悬浮时显示/隐藏音量条
    function hide() {
        volume_slider.hover(() => {
            volume_slider.css({"display": "block"});
        }, hide);
        setTimeout(() => {
            if (volume_slider.is(":hover") || volumeIcon.is(":hover")) {
                volume_slider.css({"display": "block"});
            } else {
                volume_slider.css({"display": "none"});
            }
        }, 1000)
    }


})