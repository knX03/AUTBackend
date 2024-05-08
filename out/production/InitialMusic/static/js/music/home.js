new Vue({
    el: '#app',
    data() {

        return {
            messageCode: "",
            searchValue: '',
            searchList: [],
            user: {
                user_ID: '',
                user_Name: '',
                user_Avatar: '../photos/logo/avatarWhite.png',
            },

            /*日常推荐图片*/
            DailyRe: '../photos/background/music20.jpg',
            /*轮播图列表*/
            swiperImg: [
                {
                    id: "0",
                    idView: '../photos/userAvatar/IMG_5185.PNG',
                    url: '/music/SecondaryPage/SingerDetail.html',
                    type: "singer_ID",
                    value: "周杰伦"
                },
                {
                    id: "1", idView: '../photos/singerAvatar/JJ.jpg', url: '/music/SecondaryPage/SingerDetail.html',
                    type: "singer_ID",
                    value: "林俊杰"
                },
                {
                    id: "2", idView: '../photos/albumCover/我很忙.jpg', url: '/music/SecondaryPage/AlbumDetail.html',
                    type: "album_Name",
                    value: "我很忙"
                },
                {
                    id: "3", idView: '../photos/userAvatar/IMG_5185.PNG', url: '/music/SecondaryPage/SingerDetail.html',
                    type: "singer_ID",
                    value: "周杰伦"
                },
                {
                    id: "4", idView: '../photos/albumCover/学不会.jpg', url: '/music/SecondaryPage/AlbumDetail.html',
                    type: "album_Name",
                    value: "学不会"
                },
                {
                    id: "5", idView: '../photos/albumCover/魔杰座.png', url: '/music/SecondaryPage/AlbumDetail.html',
                    type: "album_Name",
                    value: "魔杰座"
                },
            ],

            /*推荐歌单列表*/
            SongPlaylist: [
                {
                    playlist_ID:'',
                    playlist_Name: '',
                    playlist_Cover: '',
                }
            ],
            /*推荐专辑列表*/
            AlbumList: [
                {
                    album_Name: '',
                    singer_ID: '',
                    album_Cover: '',
                }
            ],
        }
    },
    mounted() {
        //在跳转后页面，通过getItem方法来获取
        var FUser_ID = window.sessionStorage.getItem('user_ID');
        console.log(FUser_ID)
        this.selectUserDetail(FUser_ID)
        this.selectSongPlaylist()
        this.selectAlbum()
    },
    methods: {
        /*用户详情查询*/
        selectUserDetail(user_ID) {
            let that = this;
            axios({
                method: 'GET',
                url: 'http://localhost/user/DetailByID?user_ID=' + user_ID,
            }).then(resp => {
                if (resp.data.code === 200) {
                    that.user = resp.data.data;
                } else if (resp.data.code === 500) {
                    console.error(resp.data.msg)
                }
            })
        },
        /*轮播图跳转*/
        swiperDetail(swiperImg) {
            location.href = swiperImg.url;
            window.sessionStorage.setItem(swiperImg.type, swiperImg.value);
        },
        /*展示随机十个歌单*/
        selectSongPlaylist() {
            let that = this;
            axios({
                method: 'GET',
                url: 'http://localhost/songPlaylist/selectSongPlaylist'
            }).then(resp => {
                if (resp.data.code === 200) {
                    that.SongPlaylist = resp.data.data
                } else if (resp.data.code === 500) {
                    console.log(resp.data.msg)
                }
            })
        },

        /*展示随机十个专辑*/
        selectAlbum() {
            let that = this;
            axios({
                method: 'GET',
                url: 'http://localhost/album/selectAlbumNine'
            }).then(resp => {
                if (resp.data.code === 200) {
                    that.AlbumList = resp.data.data
                } else if (resp.data.code === 500) {
                    console.log(resp.data.msg)
                }
            })
        },

        /*显示搜索框*/
        showSearchBox() {
            let that = this;
            let searchBox = document.getElementById('searchBox');
            searchBox.style.display = 'block';
        },

        /*隐藏*/
        hiddenBox() {
            let that = this;
            let searchBox = document.getElementById('searchBox');
            searchBox.style.display = 'none';
        },

        /*搜索*/
        search() {
            let that = this;
            if (this.searchValue === null || this.searchValue.length <= 0) {
                this.hiddenBox()
                return;
            }
            console.log(this.searchValue)
            axios({
                method: 'get',
                url: 'http://localhost/song/searchSong?searchValue=' + this.searchValue,
            }).then(resp => {
                that.searchList = resp.data.data
                this.showSearchBox();
            })
        },


        /*todo 退出登录*/
        exitLogin() {
            location.href = "http://localhost/music/LRF/SignIn.html";
            window.sessionStorage.setItem('user_ID', null);
        },

        /*用户详情跳转*/
        toUserInfo(row) {
            location.href = "/music/userInfo.html";
        },
        /*歌单详情跳转*/
        toSongPlaylist(row) {
            location.href = "/music/SecondaryPage/songPlaylistsDetail.html";
            window.sessionStorage.setItem('playlist_ID', row);
        },
        /*专辑详情跳转*/
        toAlbum(row) {
            location.href = "/music/SecondaryPage/AlbumDetail.html";
            window.sessionStorage.setItem('album_Name', row);
        },
        /*歌手详情跳转*/
        toSinger(row) {
            location.href = "/music/SecondaryPage/SingerDetail.html";
            window.sessionStorage.setItem('singer_ID', row);
        },
    }
})
