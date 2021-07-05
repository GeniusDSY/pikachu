import React from 'react'
import { Layout, Form, Select, Button, Input, InputNumber } from 'antd';
import { withRouter } from 'react-router'
import Axios from 'axios'
import { successMsg, failMsg } from '../../../UtilFunction'

const { Content } = Layout;
const { Option } = Select;

class AdUnitContent extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            data: [],
            value: undefined,
            planMsg: [],
            adUnitDTO: {
                planId: 0,
                unitName: "",
                positionType: 0,
                budget: 0
            }
        }
    }

    onFinish(props) {
        this.setState({
            adUnitDTO: {
                planId: parseInt(props.planId.replace(/([.][^/]+)$/, "")),
                unitName: props.unitName,
                positionType: parseInt(props.positionType),
                budget: props.budget
            }
        })
        this.createAdUnit();
    }

    onFinishFailed(props) {
        console.log(props.errorInfo)
    }

    createAdUnit() {
        console.log(this.state.adUnitDTO)
        Axios({
            method: "POST",
            url: "http://localhost:8080/pikachu/ad-sponsor/adUnit/create/unit",
            data: this.state.adUnitDTO
        }).then(function (response) {
            console.log(response.data.message)
            successMsg(response.data.message);
        }).catch(function (error) {
            failMsg("发生错误！！");
        });
    }

    componentDidMount() {
        let that = this;
        Axios({
            url: "http://localhost:8080/pikachu/ad-sponsor/adPlan/getAdPlanMsg",
            params: {
                userId: 15
            }
        }).then(function (response) {
            that.setState({
                planMsg: response.data.data
            })
        }).catch(function (error) {
            console.log(error);
        });
    }

    render() {
        return (
            <>
                <Content style={{ padding: '50px' }}>
                    <Form
                        {...{
                            labelCol: {
                                span: 8,
                            },
                            wrapperCol: {
                                span: 16,
                            }
                        }}
                        name="basic"
                        initialValues={{
                            remember: true,
                        }}
                        onFinish={this.onFinish.bind(this)}
                        onFinishFailed={this.onFinishFailed.bind(this)}
                    >
                        <Form.Item
                            label="广告计划Id"
                            name="planId"
                            rules={[
                                {
                                    required: true,
                                    message: '请输入广告投放计划Id!',
                                },
                            ]}
                        >
                            <Select
                                showSearch
                                style={{ width: 400 }}
                                placeholder="选择需要绑定的广告投放计划id"
                                optionFilterProp="children"
                                filterOption={(input, option) =>
                                    option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                }
                                filterSort={(optionA, optionB) =>
                                    optionA.children.toLowerCase().localeCompare(optionB.children.toLowerCase())
                                }
                            >
                                {
                                    this.state.planMsg.map((plan, index) => {
                                        return (<Option key={index} value={plan}>{plan}</Option>)
                                    })
                                }
                            </Select>
                        </Form.Item>
                        <Form.Item
                            label="广告单元名称"
                            name="unitName"
                            rules={[
                                {
                                    required: true,
                                    message: '请输入广告单元名称!',
                                },
                            ]}
                        >
                            <Input style={{ width: 400 }} placeholder="🐇🐇给广告单元起个好听的名字吧！🐇🐇" />
                        </Form.Item>
                        <Form.Item label="广告位置类型"
                            name="positionType"
                            rules={[
                                {
                                    required: true,
                                    message: '请选择广告位置类型!',
                                },
                            ]}>
                            <Select
                                showSearch
                                style={{ width: 400 }}
                                placeholder="选择广告位置类型"
                            >
                                <Option value="1">开屏广告</Option>
                                <Option value="2">贴片广告</Option>
                                <Option value="4">中部贴片广告</Option>
                                <Option value="8">暂停贴片广告</Option>
                                <Option value="16">后部贴片</Option>
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
                        >
                            <InputNumber
                                style={{width: 400}}
                                formatter={value => `${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}
                                parser={value => value.replace(/\$\s?|(,*)/g, '')}
                                min={0}
                                max={9999999999999}
                            />
                        </Form.Item>

                        <Form.Item {...{ wrapperCol: { offset: 8, span: 16, } }} style={{marginTop:"30px"}}>
                            <Button type="primary" htmlType="submit" style={{width:"400px"}}>
                                提交
                            </Button>
                        </Form.Item>
                    </Form>
                </Content>
            </>
        )
    }
}

export default withRouter(AdUnitContent)