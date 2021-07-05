import React from 'react'
import { Layout, Form, Button, Select, Input } from 'antd';
import { MinusCircleOutlined, PlusOutlined } from '@ant-design/icons';
import { withRouter } from 'react-router';
import Axios from 'axios';

import { successMsg, failMsg } from '../../UtilFunction'

const { Content } = Layout;
const { Option } = Select;

class AdItContent extends React.PureComponent {

    constructor(props) {
        super(props);
        this.state = {
            planMsg: [],
            unitIts: [],
            adUnitItDTO: {}
        }
    }

    componentDidMount() {
        let that = this;
        Axios({
            url: "http://localhost:8080/pikachu/ad-sponsor/adUnit/getAdUnitMsg",
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

    unitIdChange(value, index) {
        let unitIts = this.state.unitIts;
        if (unitIts.length === index) {
            unitIts.push({
                unitId: parseInt(value.replace(/([.][^/]+)$/, "")),
                // itTag: this.state.itTag
            })
        } else {
            unitIts[index].unitId = parseInt(value.replace(/([.][^/]+)$/, ""))
        }
        this.setState({
            unitIts: unitIts
        })
    }

    itTagChange(value, index) {
        let unitIts = this.state.unitIts;
        if (unitIts.length === index) {
            unitIts.push({ itTag: value })
        } else {
            unitIts[index].itTag = value
        }
        this.setState({
            unitIts: unitIts
        }, () => {
            console.log()
        })
    }

    createUnitIt() {
        Axios({
            method: "POST",
            url: "http://localhost:8080/pikachu/ad-sponsor/adUnit/create/unitIt",
            data: this.state.adUnitItDTO
        }).then(function (response) {
            successMsg(response.data.message);
        }).catch(function (error) {
            console.log("Tets")
        })
    }

    onFinish() {
        let adUnitItDTO = this.state.adUnitItDTO;
        adUnitItDTO.unitIts = this.state.unitIts;
        this.setState=({
            adUnitItDTO: adUnitItDTO
        })
        this.createUnitIt();
    }

    onFinishFailed() {

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
                        <Form.List
                            name={'unitIts'}
                        >
                            {(fields, { add, remove }, { errors }) => (
                                <>
                                    {fields.map((field, index) => (
                                        <Form.Item
                                            key={field.key}

                                            validateTrigger={['onChange', 'onBlur']}
                                        >
                                            <Form.Item
                                                {...{
                                                    labelCol: {
                                                        span: 12,
                                                    },
                                                    wrapperCol: {
                                                        span: 12,
                                                    }
                                                }}
                                                {...field}
                                                label="广告单元Id"
                                                rules={[
                                                    {
                                                        required: true,
                                                        message: '请选择需要绑定的推广单元Id!',
                                                    },
                                                ]}
                                            >
                                                <Select
                                                    showSearch
                                                    style={{ width: 400 }}
                                                    placeholder="选择需要绑定的广告推广单元id"
                                                    optionFilterProp="children"
                                                    filterOption={(input, option) =>
                                                        option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
                                                    }
                                                    filterSort={(optionA, optionB) =>
                                                        optionA.children.toLowerCase().localeCompare(optionB.children.toLowerCase())
                                                    }
                                                    onChange={(value) => { this.unitIdChange(value, index) }}
                                                >
                                                    {
                                                        this.state.planMsg.map((plan, index) => {
                                                            return (<Option key={index} value={plan}>{plan}</Option>)
                                                        })
                                                    }
                                                </Select>
                                            </Form.Item>

                                            <Form.Item
                                                {...{
                                                    labelCol: {
                                                        span: 12,
                                                    },
                                                    wrapperCol: {
                                                        span: 12,
                                                    }
                                                }}
                                                rules={[
                                                    {
                                                        required: true,
                                                        message: '请选择兴趣爱好领域'
                                                    }
                                                ]}
                                                label={"兴趣爱好限制"}
                                            >
                                                <Input style={{ width: 400 }}
                                                    placeholder="输入该投放单元所在的兴趣爱好领域吧！"
                                                    onChange={(e) => { this.itTagChange(e.target.value, index) }}
                                                />

                                                {fields.length > 1 ? (
                                                    <MinusCircleOutlined
                                                        className="dynamic-delete-button"
                                                        onClick={() => remove(field.name)}
                                                    />
                                                ) : null}
                                            </Form.Item>
                                        </Form.Item>
                                    ))}
                                    <Form.Item {...{ wrapperCol: { offset: 8, span: 16, } }} style={{ marginTop: "30px" }}>
                                        <Button
                                            type="dashed"
                                            onClick={() => add()}
                                            icon={<PlusOutlined />}
                                            style={{ width: "400px" }}
                                        >
                                            增加新的兴趣爱好标签
                                        </Button>
                                        <Form.ErrorList errors={errors} />
                                    </Form.Item>
                                </>
                            )}

                        </Form.List>
                        <Form.Item {...{ wrapperCol: { offset: 8, span: 16, } }} style={{ marginTop: "30px" }}>
                            <Button type="primary" htmlType="submit" style={{ width: "400px" }}>
                                提交
                            </Button>
                        </Form.Item>
                    </Form>
                </Content>
            </>
        )
    }
}

export default withRouter(AdItContent)