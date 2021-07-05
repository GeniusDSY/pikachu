import React from 'react';
import { withRouter } from 'react-router-dom';
import { Layout, Row, Col, Menu, BackTop } from "antd";
import IndexContent from "./../../components/Content/IndexContent";
import SponsorContent from "./../../components/Content/Sponsor/SponsorContent";
import SearchContent from "../../components/Content/Search/SearchContent";
import './Index.styl';
// import Icon from "./../../assets/logo20.png";

const { Header } = Layout;

class Index extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      content: '1',
      isLogin: false,
      collapsible: true
    }
  }

  contentHandle(props) {
    this.setState({
      content: props.key
    })
  }

  render() {
    return (
      <Layout>
        <Header className="header">
          <Row>
            <Col span={2}>
              <div style={{ color: 'gray', fontSize: '24px' }}>Pikachu</div>
            </Col>
            <Col span={19}>
              <Menu theme="dark" mode="horizontal" defaultSelectedKeys={['1']} onClick={this.contentHandle.bind(this)}>
                <Menu.Item key="1">主页</Menu.Item>
                <Menu.Item key="2">广告投放</Menu.Item>
                <Menu.Item key="3">广告检索</Menu.Item>
              </Menu>
            </Col>
            <Col span={3}>
            </Col>
          </Row>
        </Header>
        <Content content={this.state.content} />
        <BackTop />
      </Layout>
    );
  }
}

function Content(props) {
  if (props.content === "3") {
    return (<SearchContent />)
  } else if (props.content === "2") {
    return (<SponsorContent />)
  } else {
    return (<IndexContent />)
  }
}

export default withRouter(Index)