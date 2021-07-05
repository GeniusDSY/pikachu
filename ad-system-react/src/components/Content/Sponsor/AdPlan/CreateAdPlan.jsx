import React from 'react'
import { Layout, Form, Input, Button, DatePicker } from 'antd';
import { withRouter } from 'react-router'
import Axios from 'axios'
import {successMsg, failMsg} from '../../../UtilFunction'

const {Content} = Layout
const { RangePicker } = DatePicker;

class CreateAdPlan extends React.PureComponent {

    constructor(props) {
        super(props);
        this.state = {
            data: {
                planName:"",
	            startDate:"",
	            endDate:""
            }
        }
    }

    onFinish(props) {
        const dateFormat = "YYYY-MM-DD HH:mm:ss";
        this.setState({
            data: {
                planName:props.planName,
                startDate:props.time[0].format(dateFormat),
                endDate:props.time[1].format(dateFormat)
            }
        })
        this.createAdPlan()
    }

    createAdPlan() {
        Axios({
            method: "POST",
            url:"http://localhost:8080/pikachu/ad-sponsor/adPlan/create",
            data:{
                userId:15,
                planName:this.state.data.planName,
                startDate:this.state.data.startDate,
                endDate:this.state.data.endDate
            }
        }).then(function (response) {
            successMsg(response.data.data);
        }).catch(function (error) {
            failMsg();
        });
    }

    onFinishFailed(props) {
        console.log(props.errorInfo)
    }

    render() {
        return (
            <>
                <Content style={{ padding: '50px'}}>
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
                            label="广告计划名称"
                            name="planName"
                            rules={[
                                {
                                    required: true,
                                    message: '请输入广告计划名称!',
                                },
                            ]}
                        >
                            <Input style={{width:"400px"}} placeholder="🙈🙈给广告计划起个好听的名字吧！🙈🙈"  />
                        </Form.Item>

                        <Form.Item
                            label="计划起止时间"
                            name="time"
                            rules={[
                                {
                                    required: true,
                                    message: '请设置广告计划起止时间!',
                                },
                            ]}
                        >
                            <RangePicker showTime />
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

export default withRouter(CreateAdPlan)