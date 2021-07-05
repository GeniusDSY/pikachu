import React, { Fragment } from 'react'
import './style.css';
import { Layout, Popover, Cascader, Button, Image, Col, Row, Skeleton, Divider, Collapse, Input, Select, Switch, PageHeader, Card } from 'antd';
import { withRouter } from "react-router";
import Options from './getPC'
import axios from 'axios';

const { Meta } = Card;
const { Option } = Select;
const { Panel } = Collapse;
const { Footer, Content } = Layout;
const options = Options;

class SearchContent extends React.PureComponent {
    constructor(props) {
        super(props);
        this.state = {
            Player: null,
            videoUrl: '',
            req: {
                "featureInfo": {
                    "districtFeature": {
                        "districts": [ // 地理位置
                            {
                                // "city": "合肥市",
                                // "province": "安徽省"
                            }
                        ]
                    },
                    "itFeature": { // 兴趣爱好
                        "its": [
                            //   "台球",
                            //   "游泳"
                        ]
                    },
                    "keywordFeature": { // 关键字
                        "keywords": [
                            //   "宝马",
                            //   "大众"
                        ]
                    },
                    "relation": "OR" // 检索关系
                },
                "mediaId": "pikachu", // 写死
                "requestInfo": {
                    "adSlots": [
                        {
                            "adSlotCode": "ad_main", // 主广告位
                            "height": 720, // 高度
                            "minCpm": 1000, // 最小千人成本，没什么用，写死吧
                            "positionType": 2, // 主广告位类型，写死
                            "type":
                                [ // 广告类型，1：图片 2： 视频 3： 文本
                                    // 1,
                                    // 2
                                ],
                            "width": 1080 // 宽度，写死 
                        },
                        {
                            "adSlotCode": "ad_extra", // 副广告位
                            "height": 720, // 高度
                            "minCpm": 1000, // 最小千人成本
                            "positionType": 1, // 副广告位类型，写死
                            "type": [// 广告类型，1：图片 2： 视频 3： 文本
                                // 1,
                                // 2
                            ],
                            "width": 800 // 宽度，写死
                        }
                    ],
                    "app": {
                        "activityName": "search",
                        "appCode": "pikachu",
                        "appName": "pikachu",
                        "packageName": "cn.edu.cqupt"
                    },
                    "device": {
                        "deviceCode": "iphone",
                        "displaySize": "1080 720",
                        "ip": "127.0.0.1",
                        "mac": "70-4D-7B-4A-63-2D",
                        "model": "x",
                        "screenSize": "1080 720",
                        "serialName": "123456789"
                    },
                    "geo": {
                        "city": "北京市",
                        "latitude": 100.28,
                        "longitude": 88.61,
                        "province": "北京市"
                    },
                    "requestId": "pikachu"
                }
            },
            res: {
            },
            geogValue: ['', "省/市/区"],
            adType: ["图片", "视频", "文本"],
            cityValue: "",
        }
    }

    // 使用正则来去除输入框中的无关字符
    reg = (str) => {
        // 将空格转换为字符 ，
        str = str.replace(/\s/g, '，')
        // 以这些无关字符为分割形成数组
        str = str.split(/[,，；;.。]/g)
        let arr = []
        str = str.map(item => {
            // 去除数组中的空串元素
            if (item !== '') {
                return arr.push(item)
            }
        })
        // 返回成功的数组
        return arr;
    }

    //获取地理位置的数据
    geogOnChange = (value) => {
        console.log(value.length === 0, Object.keys(value).length === 0)
        let judge = false;
        if (value instanceof Array) {
            judge = value.length === 0;
        }
        if (value instanceof Object) {
            judge = Object.keys(value).length === 0;
        }
        if (!judge) {
            this.setState({
                geogValue: value
            }, () => {
                let geogData = this.state.geogValue
                let geogObj = [
                    {
                        "city": geogData[1],
                        "province": geogData[0]
                    }
                ]
                let req = this.state.req
                req.featureInfo.districtFeature.districts = geogObj
                this.setState({
                    req,
                    cityValue: geogData[1]
                }
                )
            }
            )
        } else {
            console.log(this.state.geogValue)
            this.setState({
                geogValue: ["", "省/市/区"],
                cityValue: ""
            })
        }
    }

    // 渲染广告类型下拉框的选项
    showAdType = () => {
        return this.state.adType.map((item, index) => {
            return <Option key={index}>{item}</Option>
        })
    }

    // 获取兴趣爱好的数据
    hobOnChange = (e) => {
        let req = this.state.req
        req.featureInfo.itFeature.its = this.reg(e.target.value)
        this.setState({
            req
        })
    }

    // 获取关键字的数据
    kwOnChange = (e) => {
        let req = this.state.req
        req.featureInfo.keywordFeature.keywords = this.reg(e.target.value)
        this.setState({
            req
        })
    }

    //获取广告类型的数据
    adTypeOnChange = (value) => {
        let adTypeData = value.map(item => {
            return item * 1 + 1
        })
        let req = this.state.req
        req.requestInfo.adSlots[0].type = adTypeData
        req.requestInfo.adSlots[1].type = adTypeData
        this.setState({
            req
        })
    }

    // 获取检索关系的数据
    switchOnChange = (e) => {
        let req = this.state.req
        if (e === true) {
            req.featureInfo.relation = "OR"
        } else {
            req.featureInfo.relation = "AND"
        }
        this.setState({
            req
        })
    }

    // 发送请求获取数据
    submit = () => {
        const req = this.state.req
        axios.post("http://localhost:8080/pikachu/ad-search/fetchAds", req)
            .then((res) => {
                this.setState({
                    res
                })
            })
            .catch((err) => {
                console.log(err)
            })
    }

    // 判断main应该展示骨架屏还是广告内容
    judgeMain = () => {
        if (this.state.res.status === 200) {
            if (this.state.res.data.data.adSlot2Ads.ad_main !== undefined) {
                return this.showAdMain()
            }
        } else {
            return (
                <Fragment>
                    <Skeleton />
                    <Divider />
                    <Skeleton />
                    <Divider />
                    <Skeleton />
                </Fragment>
            )
        }
    }

    // 渲染Ad-main的每一条广告内容
    showAdMain = () => {
        let city = "";
        console.log(this.state.cityValue !== undefined)
        if (this.state.cityValue !== undefined
            && this.state.cityValue !== null
            && this.state.cityValue !== "") {
            city = "[" + this.state.cityValue  + "专属链接!" + "]　";
        }
        return this.state.res.data.data.adSlot2Ads.ad_main.map((item, index) => {
            return (
                <>
                    <Fragment>
                        <a href={item.adUrl} style={{ paddingLeft: 5 }} id='mainA' target="_blank">
                            {
                                city + item.adName
                            }
                        </a>
                        <Row gutter={20} style={{ marginBottom: 0 }}>
                            {this.showAdMainItem(item.adContents, item.materialType, item.adUrl)}
                        </Row>
                    </Fragment>
                </>
            )
        })
    }

    // 渲染Ad-main的每一条广告中的几个广告图片
    showAdMainItem = (value, materialType, url) => {
        console.log("materialType:", materialType, "value: ", value);
        // 文字类广告特殊处理
        if (materialType === 5) {
            return (
                <>
                    <Col style={{ marginBottom: 10, padding: 0 }} id="mainCol" span={8}>
                        <div style={{ textAlign: 'center' }}>
                            <a href={value[0].clickUrl} target="_blank">
                                <Image
                                    preview={false}
                                    src={value[0].showUrl}
                                    alt={value[0].adDesc}
                                    style={{ width: 210, height: 280 }}
                                />
                            </a>
                        </div>
                    </Col>
                    <Col style={{ marginBottom: 10, padding: 0 }} id="mainCol" span={16}>
                        <div>
                            <a href={value[0].clickUrl} target="_blank" id="otherA" title={value[0].adDesc}>
                                {value[0].adDesc}
                            </a>
                        </div>
                    </Col>
                </>
            )
        } else {
            return (
                value.map((item, index) => {
                    if (item.materialType === 1 || item.materialType === 2) {
                        return (
                            <Col style={{ marginBottom: 20, padding: 0 }} id="mainCol" span={8}>
                                <a href={item.clickUrl} target="_blank">
                                    <Card
                                        hoverable
                                        style={{ width: 240, height: 300 }}
                                        cover={<img alt={item.adDesc} style={{ width: 240, height: 180 }} src={item.showUrl} />}
                                    >
                                        <Meta title={item.adDesc} description={url} />
                                    </Card>
                                </a>
                            </Col>
                        );
                    } else if (item.materialType === 3 || item.materialType === 4) {
                        let playNameStyle = {
                            width: 240,
                            height: 180,
                            marginRight: 20,
                            backgroundImage: 'url(' + item.showUrl + ')'
                        }
                        return (
                            <a href={item.clickUrl} target="_blank">
                                <div className="play_name" style={playNameStyle}>
                                    <div className="ico_play"></div>
                                </div>
                                <div style={{ textAlign: 'center', width: 240 }}>
                                    {item.adDesc}
                                </div>
                            </a>
                        );
                    }
                }
                )
            )
        }
    }

    // 判断extra应该展示骨架屏还是广告内容
    judgeExtra = () => {
        if (this.state.res.status === 200) {
            if (JSON.stringify(this.state.res.data.data.adSlot2Ads.ad_extra) !== undefined) {
                let data = Object.keys(this.state.res.data.data.adSlot2Ads.ad_extra).map(key => {
                    return this.state.res.data.data.adSlot2Ads.ad_extra[key];
                })
                if (JSON.stringify(data) !== "[]" && data.length !== 0) {
                    return this.showAdExtra();
                }
            }
        } else {
            return (
                <Fragment>
                    <Skeleton />
                </Fragment>
            )
        }
    }

    // 渲染Ad-extra的广告内容
    showAdExtra = () => {
        return this.state.res.data.data.adSlot2Ads.ad_extra[0].adContents.map((item, index) => {
            return (
                <Col style={{ marginBottom: 10, textAlign: 'center' }} span={8}>
                    <a href={item.clickUrl} target="_blank">
                        <Image
                            preview={false}
                            src={item.showUrl}
                            alt={item.adDesc}
                            style={{ width: 150, height: 120 }}
                        />
                    </a>
                    <a href={item.clickUrl} target="_blank" id="otherA" title={item.adDesc}>
                        {item.adDesc}
                    </a>
                </Col>
            )
        })
    }

    handleClick = e => {
        this.setState({ current: e.key });
    };

    render() {
        console.log(this.state.res)
        return (
            <>
                <Layout>
                    <Content className="site-layout" style={{ padding: '0px 0px', width: '100%', backgroundColor: 'white' }}>
                        {/* 顶部填写信息部分 */}
                        <PageHeader
                            className="site-page-header"
                        >
                            {/* 地理位置 */}
                            <Popover content={<Cascader options={options} onChange={this.geogOnChange} placeholder="请选择你的地理位置" />} title="地理位置">
                                <Button type="primary" id='geog'>
                                    {`  ${this.state.geogValue[1]}`}
                                </Button>
                            </Popover>

                            {/* 兴趣爱好 */}
                            <Input
                                placeholder="兴趣爱好"
                                style={{ width: 300, marginRight: 20 }}
                                onChange={this.hobOnChange}
                            />

                            {/* 关键字 */}
                            <Input
                                placeholder="关键字"
                                style={{ width: 300 }}
                                onChange={this.kwOnChange}
                            />

                            {/* 广告类型 */}
                            <Select
                                mode="multiple"
                                allowClear
                                style={{ width: '100%' }}
                                placeholder="广告类型"
                                style={{ width: 250, marginLeft: 20 }}
                                onChange={this.adTypeOnChange}
                            >
                                {this.showAdType()}
                            </Select>

                            {/* 检索关系 */}
                            <Switch
                                checkedChildren="or"
                                unCheckedChildren="and"
                                defaultChecked
                                style={{ marginLeft: 20 }}
                                onChange={this.switchOnChange}
                            />

                            {/* 提交按钮 */}
                            <Button
                                type="primary"
                                style={{ marginLeft: 20 }}
                                onClick={this.submit}
                            >
                                搜索
                            </Button>


                        </PageHeader>
                        {/* 渲染内容部分 */}
                        <Row style={{ paddingLeft: 145, paddingRight: 50 }}>
                            {/* ad-main */}
                            <Col span={12}>
                                {this.judgeMain()}
                            </Col>
                            {/* 中间空白 */}
                            <Col span={2}>
                            </Col>
                            {/* ad-extra */}
                            <Col span={8} style={{ textAlign: 'center', }}>
                                <Collapse defaultActiveKey={['1']} ghost
                                    style={{ textAlign: 'left' }}
                                    expandIconPosition='right'
                                >
                                    <Panel header="相关产品" key="1">
                                        <Row gutter={20}>
                                            {/* 渲染右边extra广告 */}
                                            {this.judgeExtra()}
                                        </Row>
                                    </Panel>
                                </Collapse>
                            </Col>
                            {/* 右边空白 */}
                            <Col span={4} style={{ height: 600 }}>
                            </Col>
                        </Row>
                    </Content>
                </Layout>
                <Footer style={{ textAlign: 'center', padding: "5px 50px" }}>⚡PiKachu⚡ ©2021 Created by GeniusDSY</Footer>
            </>
        )
    }
}

// function AdContent(props) {
//     console.log("TEST: ", props)
//     if (props.item.materialType === 1 || props.item.materialType === 2) {
//         return (
//             <>
//                 <Image
//                     preview={false}
//                     src={props.item.showUrl}
//                     alt={props.item.adDesc}
//                     style={{ width: 200, height: 120 }}
//                 />
//             </>
//         );
//     } else if (props.item.materialType === 3 || props.item.materialType === 4) {
//         let playNameStyle = {
//             width: 200,
//             height: 120,
//             backgroundImage: 'url(' + props.item.showUrl + ')'
//         }
//         return (
//             <div class="play_name" style={playNameStyle}>
//                 <div class="ico_play"></div>
//             </div>
//         );
//     }
// }

export default withRouter(SearchContent)
