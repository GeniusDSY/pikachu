import React from 'react'
import { Menu, Layout } from 'antd';
import { ScheduleOutlined, TagOutlined, SmileOutlined, StarOutlined } from '@ant-design/icons';
import CreateAdPlan from './AdPlan/CreateAdPlan'
import AllAdPlans from './AdPlan/AllAdPlans'
import CreateAdUnit from './AdUnit/CreateAdUnit'
import AllAdUnits from './AdUnit/AllAdUnits'
import AllCreatives from './AdCreative/AllCreatives';
import CreateAdCreative from './AdCreative/CreateAdCreative'
import AdDistrictContent from './AdDistrictContent'
import AdItContent from './AdItContent'
import AdKeywordContent from './AdKeywordContent'

import { withRouter } from "react-router"

const { SubMenu } = Menu;
const { Footer, Sider, Content } = Layout;

class SponsorContent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            collapsed: false,
            contentId: "1",
            allAdPlans: []
        }
    }

    displayContent(props) {
        this.setState({
            contentId: props.key
        })
    }

    render() {
        return (
            <>
                <Layout className="site-layout-background">
                    <Content>
                        <Layout style={{ minHeight: '88vh', marginTop: 0 }}>
                            <Sider defaultCollapsed={false}
                                collapsible={true}
                                width={185}
                                height={"100vh"}
                                className="site-layout-background">
                                <Menu
                                    mode="inline"
                                    theme="dark"
                                    defaultSelectedKeys={['1']}
                                    style={{ height: '80%', borderRight: 0 }}
                                    onClick={this.displayContent.bind(this)}
                                >
                                    <SubMenu icon={<ScheduleOutlined />} key="AdPlan" title='广告计划'>
                                        <Menu.Item key="1">所有广告计划</Menu.Item>
                                        <Menu.Item key="2">创建广告计划</Menu.Item>
                                    </SubMenu>
                                    <SubMenu icon={<TagOutlined />} key="AdUnit" title='广告单元'>
                                        <Menu.Item key="3">所有广告单元</Menu.Item>
                                        <Menu.Item key="4">创建广告单元</Menu.Item>
                                    </SubMenu>
                                    <SubMenu icon={<SmileOutlined />} key="AdCreative" title='广告创意'>
                                        <Menu.Item key="5">所有广告创意</Menu.Item>
                                        <Menu.Item key="6">创建广告创意</Menu.Item>
                                    </SubMenu>
                                    <SubMenu icon={<StarOutlined />} key="Influencers" title='影响因素'>
                                        <Menu.Item key="7">区域</Menu.Item>
                                        <Menu.Item key="8">兴趣爱好</Menu.Item>
                                        <Menu.Item key="9">关键字</Menu.Item>
                                    </SubMenu>
                                </Menu>
                            </Sider>
                            <MyContent contentId={this.state.contentId} />
                        </Layout>
                        <Footer style={{ textAlign: 'center', padding: "5px 50px" }}>⚡PiKachu⚡ ©2021 Created by GeniusDSY</Footer>
                    </Content>
                </Layout>
            </>
        )
    }
}

function MyContent(props) {
    if (props.contentId === "1") {
        return <AllAdPlans />
    } else if (props.contentId === "2") {
        return <CreateAdPlan />
    } else if (props.contentId === "3") {
        return (<AllAdUnits />)
    } else if (props.contentId === "4") {
        return <CreateAdUnit />
    } else if (props.contentId === "5") {
        return <AllCreatives />
    } else if (props.contentId === "6") {
        return <CreateAdCreative />
    } else if (props.contentId === "7") {
        return <AdDistrictContent />
    } else if (props.contentId === "8") {
        return <AdItContent />
    } else if (props.contentId === "9") {
        return <AdKeywordContent />
    }

}

export default withRouter(SponsorContent)