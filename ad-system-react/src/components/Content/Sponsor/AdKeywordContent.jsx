import React from 'react'
import { Layout, Form, Button, Select, Input } from 'antd';
import { MinusCircleOutlined, PlusOutlined } from '@ant-design/icons';
import { withRouter } from 'react-router';
import Axios from 'axios';

import { successMsg, failMsg } from '../../UtilFunction'

const { Content } = Layout;
const { Option } = Select;

class AdKeywordContent extends React.PureComponent {

    constructor(props) {
        super(props);
        this.state = {
            planMsg: [],
            unitKeywords: [],
            adUnitKeywordDTO: {}
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
        let unitKeywords = this.state.unitKeywords;
        if (unitKeywords.length === index) {
            unitKeywords.push({
                unitId: parseInt(value.replace(/([.][^/]+)$/, "")),
            })
        } else {
            unitKeywords[index].unitId = parseInt(value.replace(/([.][^/]+)$/, ""))
        }
        this.setState({
            unitKeywords: unitKeywords
        })
    }

    keywordChange(value, index) {
        let unitKeywords = this.state.unitKeywords;
        if (unitKeywords.length === index) {
            unitKeywords.push({ keyword: value })
        } else {
            unitKeywords[index].keyword = value
        }
        this.setState({
            unitKeywords: unitKeywords
        }, () => {
            console.log()
        })
    }

    createUnitKeyword() {
        Axios({
            method: "POST",
            url: "http://localhost:8080/pikachu/ad-sponsor/adUnit/create/unitKeyword",
            data: this.state.adUnitKeywordDTO
        }).then(function (response) {
            successMsg(response.data.message);
        }).catch(function (error) {
            console.log("Tets")
        })
    }

    onFinish() {
        let adUnitKeywordDTO = this.state.adUnitKeywordDTO;
        adUnitKeywordDTO.unitKeywords = this.state.unitKeywords;
        this.setState=({
            adUnitKeywordDTO: adUnitKeywordDTO
        })
        this.createUnitKeyword();
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
                                                        message: '请选择相关的关键字'
                                                    }
                                                ]}
                                                label={"关键字限制"}
                                            >
                                                <Input style={{ width: 400 }}
                                                    placeholder="输入该投放单元相关的关键字吧！"
                                                    onChange={(e) => { this.keywordChange(e.target.value, index) }}
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
                                            增加新的相关关键字
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

export default withRouter(AdKeywordContent)