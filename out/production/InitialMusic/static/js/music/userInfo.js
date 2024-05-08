new Vue({
    el: '#app',
    data() {
        return {
            dialogVisible: false,
            dialogVisibleP: false,
            fileList: [],
            fileType: ["png", "jpg", "jpeg"],
            fileSize: 50,
            fileName: '',
            new_user_avatar: '',
            new_playlist_Cover: '../photos/logo/addPlaylist.png',
            user: {
                user_ID: '',
                user_Name: '',
                new_user_Name: '',
                user_Sex: '',
                user_Age: '',
                user_Birthday: '',
                user_Avatar: '../photos/logo/avatarWhite.png',
                user_Introduction: '',

            },
            playlist: {
                playlist_ID: '',
                playlist_Name: null,
                playlist_Cover: '',
                playlist_Introduction: '',
                create_By: '',
                create_Time: '',
            },
            creatList: [
                {
                    playlist_ID: '',
                    playlist_Name: '',
                    playlist_Cover: '',
                },
            ],
            likeList: [
                {
                    playlist_ID: '',
                    playlist_Name: '',
                    playlist_Cover: '',
                },

            ],

        }
    },
    /*/!*初始化*!/
    created() {
        /!*跳转过来未完善*!/
        var FUser_Name = window.sessionStorage.getItem('user_Name');
        this.selectUserDetail('k')
        this.selectCreateDetail('k')
        this.selectLikeDetail('k')
    },*/
    mounted() {
        var FUser_ID = window.sessionStorage.getItem('user_ID');
        this.selectUserDetail(FUser_ID)
        this.selectCreateDetail(FUser_ID)
        this.selectLikeDetail(FUser_ID)
    },

    methods: {
        /* 用户详情查询*/
        selectUserDetail(user_ID) {
            let that = this;
            axios({
                method: 'GET',
                url: 'http://localhost/user/DetailByID?user_ID=' + user_ID,
            }).then(resp => {
                if (resp.data.code === 200) {
                    that.user = resp.data.data;
                    that.new_user_avatar = resp.data.data.user_Avatar
                    console.log(that.user)
                } else if (resp.data.code === 500) {
                    console.error(resp.data.msg)
                }
                that.changeSexLogo()
            })
        },

        /*用户创建的歌单详情*/
        selectCreateDetail(user_ID) {
            let that = this;
            axios({
                method: 'GET',
                url: 'http://localhost/songPlaylist/createPlaylist?user_ID=' + user_ID,
            }).then(resp => {
                if (resp.data.code === 200) {
                    that.creatList = resp.data.data
                } else if (resp.data.code === 500) {
                    console.error(resp.data.msg)
                }
            })
        },
        /*用户收藏的歌单详情*/
        selectLikeDetail(user_ID) {
            let that = this;
            axios({
                method: 'GET',
                url: 'http://localhost/songPlaylist/likePlaylist?user_ID=' + user_ID,
            }).then(resp => {
                if (resp.data.code === 200) {
                    that.likeList = resp.data.data
                } else if (resp.data.code === 500) {
                    console.error(resp.data.msg)
                }
            })
        },
        /*点击跳转歌单详情*/
        viewSongPlaylists(row) {
            location.href = 'http://localhost/music/SecondaryPage/songPlaylistsDetail.html';
            window.sessionStorage.setItem('playlist_ID', row);
        },

        /*检查用户名是否存在*/
        checkName(row) {
            let that = this;
            if (row == null) {
                this.$message.error("名称不能为空！")
            }
            axios({
                method: 'GET',
                url: 'http://localhost/user/checkName?user_Name=' + row,
            }).then(resp => {
                if (resp.data.code === 302) {
                    this.$message.error(resp.data.msg)
                    that.user.new_user_Name = ''
                } else if (resp.data.code === 200) {
                }
            })
        },

        /*修改个人资料*/
        onSubmit() {
            let that = this;
            let newName = this.user.new_user_Name
            if (this.user.new_user_Name == null) {
                this.$message.error('重申一遍！名称不能为空！')
                return;
            }
            axios({
                method: 'POST',
                url: 'http://localhost/user/changeUserInfo?new_user_Name=' + that.user.new_user_Name,
                data: this.user,
            }).then(resp => {
                if (resp.data.code === 200) {
                    that.dialogVisible = false
                    this.$message.success(resp.data.msg)
                    console.log(resp.data.data)
                    that.selectUserDetail(resp.data.data)
                } else if (resp.data.code === 500) {
                    that.dialogVisible = false
                    this.$message.error(resp.data.msg)
                }
                that.changeSexLogo()
            })
        },
        /*取消修改*/
        catchSubmit() {
            this.dialogVisible = false
            this.new_user_avatar = this.user.user_Avatar
        },

        /*上传头像前的验证*/
        beforeUpload(file) {
            if (file.type !== "" || file.type != null || file.type !== undefined) {
                //截取文件的后缀，判断文件类型
                const FileExt = file.name.replace(/.+\./, "").toLowerCase();
                //计算文件的大小
                const isLt5M = file.size / 1024 / 1024 < 20; //这里做文件大小限制
                //如果大于50M
                if (!isLt5M) {
                    this.$message('上传文件大小不能超过 20MB!');
                    return false;
                }
                //如果文件类型不在允许上传的范围内
                if (this.fileType.includes(FileExt)) {
                    this.new_user_avatar = URL.createObjectURL(new Blob([file]));//赋值图片的url，用于图片回显功能
                    this.new_playlist_Cover = URL.createObjectURL(new Blob([file]));
                    return true;
                } else {
                    this.$message.error("上传文件格式不正确!");
                    return false;
                }

            }
        },

        /*上传头像*/
        uploadAvatar(item) {
            let that = this;
            this.$message.success("头像正在上传！")
            let FormDatas = new FormData()
            FormDatas.append('file', item.file);
            axios({
                method: 'post',
                url: 'http://localhost/user/uploadAvatar',
                headers: this.headers,
                data: FormDatas
            }).then(resp => {
                if (resp.data.code === 200) {
                    this.$message.success("头像上传成功！")
                } else {
                    this.$message({
                        message: "上传失败！",
                        duration: 1000
                    });
                }
            })
        },
        /*新建歌单*/
        createPlaylist() {
            let that = this;
            this.playlist.create_By = this.user.user_ID
            console.log(this.playlist.playlist_Name)
            if (this.playlist.playlist_Name == null) {
                this.$message.error('重申一遍！名称不能为空！')
                return;
            }
            axios({
                method: 'POST',
                url: 'http://localhost/songPlaylist/createNewPlaylist',
                data: this.playlist,
            }).then(resp => {
                if (resp.data.code === 200) {
                    that.dialogVisibleP = false
                    this.playlist.playlist_Name = null
                    this.playlist.playlist_Cover = ''
                    this.playlist.playlist_Introduction = ''
                    this.playlist.create_By = ''
                    this.playlist.create_Time = ''
                    this.$message.success(resp.data.msg)
                    that.selectCreateDetail(that.user.user_ID)
                } else if (resp.data.code === 500) {
                    that.dialogVisibleP = false
                    this.$message.error(resp.data.msg)
                }
            })
        },

        /*检查歌单名是否存在*/
        checkPlaylistName(row) {
            let that = this;
            console.log(row)
            if (row == null) {
                this.$message.error("名称不能为空！")
            }
            axios({
                method: 'GET',
                url: 'http://localhost/songPlaylist/checkPlaylistName?playlist_Name=' + row,
            }).then(resp => {
                if (resp.data.code === 302) {
                    this.$message.error(resp.data.msg)
                    that.playlist.playlist_Name = ''
                } else if (resp.data.code === 200) {
                }
            })
        },

        handleClose(done) {
            this.playlist.playlist_Name = null
            this.playlist.playlist_Cover = ''
            this.playlist.playlist_Introduction = ''
            this.playlist.create_By = ''
            this.playlist.create_Time = ''
            done();
        },
        /*上传歌单封面*/
        uploadCover(item) {
            let that = this;
            this.$message.success("封面正在上传！")
            //上传文件的需要formdata类型;所以要转
            let FormDatas = new FormData()
            FormDatas.append('file', item.file);
            axios({
                method: 'post',
                url: 'http://localhost/songPlaylist/uploadCover',
                headers: this.headers,
                data: FormDatas
            }).then(resp => {
                if (resp.data.code === 200) {
                    this.$message.success("封面上传成功！")
                } else {
                    this.$message({
                        message: "上传失败！",
                        duration: 1000
                    });
                }
            })
        },
        /*不同的性别对应不同的logo*/
        changeSexLogo() {
            let that = this;
            switch (this.user.user_Sex) {
                case '男':
                    $('#sexLogo').attr("src", "../photos/logo/boy.png")
                    break
                case '女':
                    $('#sexLogo').attr("src", "../photos/logo/girl.png")
                    break
                case '外星人':
                    $('#sexLogo').attr("src", "../photos/logo/alien.png")
                    break
                case '沃尔玛':
                    $('#sexLogo').attr("src", "../photos/logo/wal.png")
                    break
                case '不被定义的':
                    $('#sexLogo').attr("src", "../photos/logo/alien.png")
                    break
            }
        },

    }
})
