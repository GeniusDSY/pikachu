import React from 'react'
import { Spin } from 'antd'

export default () => (
  <div className="root">
    <Spin tip="正在极速加载中..." size="large" style={{
      position: 'fixed',
      left: 0,
      top: 0,
      right: 0,
      bottom: 0,
      background: 'rgba(255,255,255,0.2)',
      zIndex: 10001,
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center'
    }} />
  </div>
)