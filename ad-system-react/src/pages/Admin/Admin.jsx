import React from 'react';
import { withRouter } from 'react-router-dom';
import Player from 'xgplayer';
import {Image} from 'antd';
import {PlayCircleOutlined} from '@ant-design/icons';

class Admin extends React.Component {
  constructor(props) {
    super(props)
    this.state = ({
      Player: null,
      vedioUrl: 'http://s1.pstatp.com/cdn/expire-1-M/byted-player-videos/1.0.0/xgplayer-demo.mp4'
    })
  }

  initPlayer(url) {
    this.state.Player = new Player({
      // el、url为必选配置，其它为可选配置
      el: document.querySelector('#mse'),
      url: url,
      width: '120px',
      height: '120px',
      volume: 0.6,    // 初始音量
      autoplay: true,  // 自动播放
      playbackRate: [0.5, 0.75, 1, 1.5, 2],   // 当前播放速度
      defaultPlaybackRate: 1,     // 播放速度设置为1
      playsinline: true,
    });
  }

  render() {
    return (
      <div>
        <Image
          preview={false}
          src="https://s.cn.bing.net/th?id=ODL.6b6babc241b0850c0d8e6a7b5e6f5932&w=197&h=112&c=7&rs=1&qlt=80&o=6&dpr=1.1&pid=RichNav"
          alt="图片"
          style={{ width: 120, height: 120 }}
        />
        <PlayCircleOutlined />
      </div>
    );
  }
}

export default withRouter(Admin)