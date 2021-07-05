import React from 'react'
import { withRouter } from "react-router"
import { Layout, Carousel, Image } from "antd";

import pic1 from '../../assets/1.jpg';
import pic2 from '../../assets/2.jpg';
import pic3 from '../../assets/3.jpg';
import pic4 from '../../assets/4.jpg';
const { Content, Footer } = Layout;

const contentStyle = {
    height: '720px',
    color: '#fff',
    lineHeight: '720px',
    textAlign: 'center',
    background: '#364d79',
};

class IndexContent extends React.PureComponent {

    render() {
        return (
            <>
                <Layout>
                    <Content className="site-layout" style={{ padding: '0px 0px', width: '100%' }}>
                        <div className="site-layout-background" style={{ padding: '0px 0px 0px 0px', minHeight: 380 }}>
                            <Carousel autoplay>
                                <div>
                                    <h3 style={contentStyle}>
                                        <Image src={pic1} preview={false} width={"1735px"} height={"730px"} />
                                    </h3>
                                </div>
                                <div>
                                    <h3 style={contentStyle}>
                                        <Image src={pic2} preview={false} width={"1735px"} height={"730px"} />
                                    </h3>
                                </div>
                                <div>
                                    <h3 style={contentStyle}>
                                        <Image src={pic3} preview={false} width={"1735px"} height={"730px"} />
                                    </h3>
                                </div>
                                <div>
                                    <h3 style={contentStyle}>
                                        <Image src={pic4} preview={false} width={"1735px"} height={"730px"} />
                                    </h3>
                                </div>
                            </Carousel>
                        </div>
                    </Content>
                </Layout>
                <Footer style={{ textAlign: 'center', padding: '25px 50px 5px 50px' }}>⚡PiKachu⚡ ©2021 Created by GeniusDSY</Footer>
            </>
        )
    }
}

export default withRouter(IndexContent)