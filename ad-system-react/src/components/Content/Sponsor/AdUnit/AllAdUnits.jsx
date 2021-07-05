import React from 'react'
import { message, Layout, Empty, Row, Col, Card, Form, Input, Button, Switch, DatePicker, Select, InputNumber } from 'antd';
import { withRouter } from 'react-router'
import Axios from 'axios'
import moment from 'moment';
import { successMsg, failMsg } from '../../../UtilFunction'

const { Content } = Layout
const { Option } = Select
const dateFormat = "YYYY-MM-DD HH:mm:ss";

class AllAdPlans extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoaded: false,
            data: [],
            isEdited: new Array().fill(true),
            adUnitDTO: {}
        }
    }

    componentDidMount() {
        let that = this;
        if (that.state.isLoaded === false) {
            Axios({
                url: "http://localhost:8080/pikachu/ad-sponsor/adUnit/get/units",
                params: {
                    userId: 15
                }
            }).then(function (response) {
                that.setState({
                    data: response.data.data,
                    isEdited: new Array(response.data.data.length).fill(true),
                    isLoaded: true,
                })
            }).catch(function (error) {
                console.log(error);
            });
        }
    }

    switchChange(checked, index) {
        var isEdited = this.state.isEdited;
        isEdited[index] = !checked;
        this.setState({
            isEdited: isEdited
        })
    }

    onFinish(props) {
        console.log("props", props)
        this.setState({
            adUnitDTO: {
                id: props.id,
                planId: parseInt(props.planMsg.replace(/([.][^/]+)$/, "")),
                unitName: props.unitName,
                positionType: parseInt(this.getPositionTypeCode(props.positionType)),
                budget: props.budget,
                unitStatus: props.unitStatus === true ? 1 : 0
            }
        });
        this.updateAdUnit();
    }

    getPositionTypeCode(positionType) {
        switch (positionType) {
            case '开屏广告': return 1;
            case '贴片广告': return 2;
            case '中部贴片广告': return 4;
            case '暂停贴片广告': return 8;
            case '后部贴片广告': return 16;
            default : return 0;
        }
    }

    onFinishFailed() {

    }

    updateAdUnit() {
        let that = this;
        console.log(this.state.adUnitDTO)
        Axios({
            method: "PUT",
            url: "http://localhost:8080/pikachu/ad-sponsor/adUnit/update/unit",
            data: this.state.adUnitDTO
        }).then(function (response) {
            console.log(response)
            successMsg(response.data.message);
            that.setState({
                isEdited: new Array(that.state.data.length).fill(true),
            });
        }).catch(function (error) {
            failMsg()
        });
    }

    render() {
        var content;
        var emptyContent;
        if (JSON.stringify(this.state.data) === undefined) {
            emptyContent = <Empty image={Empty.PRESENTED_IMAGE_SIMPLE} />
        }
        let data = Object.keys(this.state.data).map(key => {
            return this.state.data[key];
        })
        if (JSON.stringify(data) === "[]" || data.length === 0) {
            emptyContent = <Empty image={Empty.PRESENTED_IMAGE_DEFAULT} />
        } else {
            content = [];
            data.forEach(element => {
                content.push(<AllAdPlanContent element={element} data={data} that={this} cardId={data.indexOf(element)} />)
            })
        }
        return (
            <>
                <Content style={{ padding: '48px' }}>
                    {emptyContent}
                    <div className="site-card-wrapper">
                        <Row gutter={16}>
                            {content}
                        </Row>
                    </div>
                </Content>
            </>
        )
    }
}

function AllAdPlanContent(props) {
    let element = props.element;
    let data = props.data;
    let that = props.that;
    let edited = that.state.isEdited
    return (
        <Col style={{ marginBottom: 10 }} span={8}>
            <Card title={element.id + "." + element.unitName}
                bordered={false}
                extra={[<Switch checked={!edited[data.indexOf(element)]} checkedChildren="编辑" unCheckedChildren="无法编辑" onChange={(checked) => that.switchChange(checked, data.indexOf(element))} />]}>
                <Form
                    {...{
                        labelCol: {
                            span: 8,
                        },
                        wrapperCol: {
                            span: 19,
                        }
                    }}
                    name="basic"
                    onFinish={that.onFinish.bind(that)}
                    onFinishFailed={that.onFinishFailed.bind(that)}
                >
                    <Form.Item
                        label="广告推广单元Id"
                        name="id"
                        hidden={true}
                        initialValue={element.id}
                    >
                    </Form.Item>
                    <Form.Item
                        label="绑定的广告计划Id"
                        name="planId"
                        hidden={true}
                        initialValue={element.planMsg}
                    >
                    </Form.Item>
                    <Form.Item
                        label="绑定的广告计划信息"
                        name="planMsg"
                        initialValue={element.planMsg}
                        rules={[
                            {
                                required: true,
                                message: '请输入新的广告计划名称!',
                            },
                        ]}
                    >
                        <Input disabled={true} style={{ width: 200 }} />
                    </Form.Item>
                    <Form.Item
                        label="广告单元名称"
                        name="unitName"
                        initialValue={element.unitName}
                        rules={[
                            {
                                required: true,
                                message: '请输入新的广告单元名称!',
                            },
                        ]}
                    >
                        <Input disabled={edited[data.indexOf(element)]} style={{ width: 200 }} />
                    </Form.Item>

                    <Form.Item
                        label="广告单元位置"
                        name="positionType"
                        initialValue={element.positionType}
                        rules={[
                            {
                                required: true,
                                message: '请输入新的广告单元位置!',
                            },
                        ]}
                    >
                        <Select
                            style={{ width: 200 }}
                            showSearch
                            placeholder="选择广告位置类型"
                            disabled={edited[data.indexOf(element)]}
                        >
                            <Option value="1">开屏广告</Option>
                            <Option value="2">贴片广告</Option>
                            <Option value="4">中部贴片广告</Option>
                            <Option value="8">暂停贴片广告</Option>
                            <Option value="16">后部贴片广告</Option>
                        </Select>
                    </Form.Item>

                    <Form.Item
                        label="预算(￥)"
                        name="budget"
                        rules={[
                            {
                                required: true,
                                message: '请输入广告单元预算!',
                            },
                        ]}
                        initialValue={element.budget}
                    >
                        <InputNumber
                            style={{ width: 200 }}
                            formatter={value => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
                            parser={value => value.replace(/\$\s?|(,*)/g, '')}
                            disabled={edited[data.indexOf(element)]}
                            min={0}
                            max={9999999999999}
                        />
                    </Form.Item>

                    <Form.Item
                        label="广告单元创建时间"
                        name="createTime"
                        initialValue={moment(element.createTime, dateFormat)}
                    >
                        <DatePicker disabled={true} showTime style={{ width: 200 }} />
                    </Form.Item>
                    <Form.Item
                        label="广告单元最后更新时间"
                        name="updateTime"
                        initialValue={moment(element.updateTime, dateFormat)}
                    >
                        <DatePicker disabled={true} showTime style={{ width: 200 }} />
                    </Form.Item>
                    <Form.Item
                        label="广告单元状态"
                        name="unitStatus"
                        valuePropName='checked'
                    >
                        <Switch className={element.id}
                            checkedChildren="有效"
                            unCheckedChildren="无效"
                            defaultChecked={element.unitStatus === 1}
                            disabled={edited[data.indexOf(element)]}
                            onChange={(checked) => {
                                if (checked === true) {
                                    element.unitStatus = 1;
                                } else {
                                    element.unitStatus = 0;
                                }
                            }}
                        />
                    </Form.Item>

                    <Form.Item {...{ wrapperCol: { offset: 8, span: 16, } }}>
                        <Button type="primary" htmlType="submit" disabled={edited[data.indexOf(element)]}>
                            提交
                            </Button>
                    </Form.Item>
                </Form>
            </Card>
        </Col>
    )
}

export default withRouter(AllAdPlans)