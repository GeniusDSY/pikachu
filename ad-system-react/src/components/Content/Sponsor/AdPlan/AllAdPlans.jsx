import React from 'react'
import { Layout, Empty, Row, Col, Card, Form, Input, Button, Switch, DatePicker } from 'antd';
import { withRouter } from 'react-router'
import Axios from 'axios'
import moment from 'moment';
import {successMsg, failMsg} from '../../../UtilFunction'

const { Content } = Layout
const dateFormat = "YYYY-MM-DD HH:mm:ss";

class AllAdPlans extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isLoaded: false,
            data: [],
            isEdited: new Array().fill(true),
            adPlanGetDTO: {
                userId: 15,
                ids: [],
                name: ""
            },
            adPlanDTO: {
                id: 0,
                userId: 15,
                planName: "",
                startDate: "",
                endDate: ""
            }
        }
    }

    componentDidMount() {
        let that = this;
        if (that.state.isLoaded === false) {
            Axios({
                method: "POST",
                url: "http://localhost:8080/pikachu/ad-sponsor/adPlan/get",
                data: that.state.adPlanGetDTO
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
        console.log(props)
        this.setState({
            adPlanDTO: {
                id: props.id,
                userId: 15,
                planName: props.planName,
                startDate: props.startDate.format(dateFormat),
                endDate: props.endDate.format(dateFormat),
                planStatus: props.planStatus === true ? 1 : 0
            }
        });
        this.updateAdPlan();
    }

    onFinishFailed() {

    }

    updateAdPlan() {
        let that = this;
        Axios({
            method: "PUT",
            url: "http://localhost:8080/pikachu/ad-sponsor/adPlan/update",
            data: this.state.adPlanDTO
        }).then(function (response) {
            console.log(response)
            that.setState({
                isEdited: new Array(that.state.data.length).fill(true),
            });
            successMsg();
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
                content.push(<AllAdPlanContent key={data.indexOf(element)} element={element} data={data} that={this} cardId={data.indexOf(element)} />)
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
        <Col key={props.cardId} style={{ marginBottom: 10 }} span={8}>
            <Card key={props.cardId} title={element.id + "." + element.planName}
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
                        label="广告Id"
                        name="id"
                        hidden={true}
                        initialValue={element.id}
                    >
                    </Form.Item>
                    <Form.Item
                        label="广告计划名称"
                        name="planName"
                        initialValue={element.planName}
                        rules={[
                            {
                                required: true,
                                message: '请输入新的广告计划名称!',
                            },
                        ]}
                    >
                        <Input disabled={edited[data.indexOf(element)]} style={{ width: 200 }} />
                    </Form.Item>

                    <Form.Item
                        label="计划开始时间"
                        name="startDate"
                        initialValue={moment(element.startDate, dateFormat)}
                    >
                        <DatePicker disabled={edited[data.indexOf(element)]} showTime style={{ width: 200 }} />
                    </Form.Item>
                    <Form.Item
                        label="计划结束时间"
                        name="endDate"
                        initialValue={moment(element.endDate, dateFormat)}
                    >
                        <DatePicker disabled={edited[data.indexOf(element)]} showTime style={{ width: 200 }} />
                    </Form.Item>
                    <Form.Item
                        label="计划状态"
                        name="planStatus"
                        valuePropName='checked'
                    >
                        <Switch className={element.id} 
                                checkedChildren="有效" 
                                unCheckedChildren="无效" 
                                defaultChecked={element.planStatus === 1} 
                                disabled={edited[data.indexOf(element)]}
                                onChange={(checked) => {
                                    if(checked === true) {
                                        element.planStatus = 1;
                                    } else {
                                        element.planStatus = 0;
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