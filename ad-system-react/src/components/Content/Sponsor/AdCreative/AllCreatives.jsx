import React from 'react'
import { Layout, Empty, Row, Col, Card, Form, Input, Button, Switch, DatePicker, InputNumber, Select } from 'antd';
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
            types: ['图片', '视频', '文本'],
            materialTypes: {
                图片: ['JPG', 'BMP'],
                视频: ['MP4', 'AVI'],
                文本: ['TXT']
            },
            displayType: '图片',
            displayMaterialType: 'JPG',
            creativeDTO: {
                id: 0,
                name: "",
                type: 0,
                materialType: 0,
                height: 0,
                width: 0,
                size: 0,
                duration: 0,
                userId: 15,
                auditStatus: 0
            }
        }
    }

    componentDidMount() {
        let that = this;
        if (that.state.isLoaded === false) {
            Axios({
                method: "GET",
                url: "http://localhost:8080/pikachu/ad-sponsor/creative/get",
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

    handleTypeChange(props) {
        console.log("props=", props, "_", this.state.materialTypes[props])
        this.setState({
            displayType: props,
            displayMaterialType: this.state.materialTypes[props][0]
        })
    }

    handleMaterialTypeChange(props) {
        this.setState({
            displayMaterialType: props
        })
    }

    getMaterialTypeIndex(typeIndex) {
        let array = Object.keys(this.state.types).map(key => {
            return this.state.types[key];
        })
        let index = 0;
        for (let i = 0; i < typeIndex; i++) {
            index += this.state.materialTypes[array[i]].length;
        }
        index += this.getIndex(this.state.materialTypes[array[typeIndex]], this.state.displayMaterialType) + 1;
        console.log(typeIndex, index);
        return index;
    }

    getIndex(data, elem) {
        let array = Object.keys(data).map(key => {
            return data[key];
        })
        for (var i = 0; i < array.length; i++) {
            if (array[i] === elem) {
                return i;
            }
        }
        return -1;//没找到则返回-1
    }

    onFinish(props) {
        this.setState({
            creativeDTO: {
                id: props.id,
                name: props.name,
                type: this.getIndex(this.state.types, this.state.displayType) + 1,
                materialType: this.getMaterialTypeIndex(this.getIndex(this.state.types, this.state.displayType)),
                height: props.height,
                width: props.width,
                size: props.size,
                duration: props.duration,
                userId: 15,
                url: props.url,
                auditStatus: props.auditStatus === true ? 1 : 0
            }
        });
        this.updateCreative();
    }

    onFinishFailed() {

    }

    updateCreative() {
        let that = this;
        console.log(that.state.creativeDTO)
        Axios({
            method: "PUT",
            url: "http://localhost:8080/pikachu/ad-sponsor/creative/update",
            data: that.state.creativeDTO
        }).then(function (response) {
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
            <Card title={element.id + "." + element.name}
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
                        label="创意Id"
                        name="id"
                        hidden={true}
                        initialValue={element.id}
                    >
                    </Form.Item>
                    <Form.Item
                        label="创意名称"
                        name="name"
                        initialValue={element.name}
                        rules={[
                            {
                                required: true,
                                message: '请输入新的创意名称!',
                            },
                        ]}
                    >
                        <Input disabled={edited[data.indexOf(element)]} style={{ width: 200 }} />
                    </Form.Item>

                    <Form.Item
                        label="创意类型"
                        name="type"
                        initialValue={element.type}
                        rules={[
                            {
                                required: true,
                                message: '请选择创意类型!',
                            },
                        ]}>
                        <Select disabled={edited[data.indexOf(element)]}
                            defaultValue={element.type}
                            name='type'
                            style={{ width: 100 }}
                            onChange={that.handleTypeChange.bind(that)}
                        >
                            {that.state.types.map(type => (
                                <Option key={type} value={type}>{type}</Option>
                            ))}
                        </Select>
                        <Select disabled={edited[data.indexOf(element)]}
                            defaultValue={element.materialType}
                            name='materialType'
                            style={{ width: 100 }}
                            onChange={that.handleMaterialTypeChange.bind(that)}
                        >
                            {that.state.materialTypes[that.state.displayType].map(materialType => (
                                <Option key={materialType}>{materialType}</Option>
                            ))}
                        </Select>

                    </Form.Item>

                    <Form.Item
                        label="高度(PX)"
                        name="height"
                        initialValue={element.height}
                        rules={[
                            {
                                required: true,
                                message: '请输入创意高度!',
                            },
                        ]}
                    >
                        <InputNumber
                            style={{ width: 200 }}
                            min={0}
                            max={9999999999999}
                            formatter={(value) => { return !isNaN(value) ? String(value).replace(/^0(0+)|[^\d]+/g, '') : '' }}
                            parser={(value) => { return !isNaN(value) ? String(value).replace(/^0(0+)|[^\d]+/g, '') : '' }}
                            disabled={edited[data.indexOf(element)]}
                        />
                    </Form.Item>

                    <Form.Item
                        label="宽度(PX)"
                        name="width"
                        initialValue={element.width}
                        rules={[
                            {
                                required: true,
                                message: '请输入创意宽度!',
                            },
                        ]}
                    >
                        <InputNumber
                            style={{ width: 200 }}
                            min={0}
                            max={9999999999999}
                            formatter={(value) => { return !isNaN(value) ? String(value).replace(/^0(0+)|[^\d]+/g, '') : '' }}
                            parser={(value) => { return !isNaN(value) ? String(value).replace(/^0(0+)|[^\d]+/g, '') : '' }}
                            disabled={edited[data.indexOf(element)]}
                        />
                    </Form.Item>

                    <Form.Item
                        label="大小(M)"
                        name="size"
                        initialValue={element.size}
                        rules={[
                            {
                                required: true,
                                message: '请输入创意大小!',
                            },
                        ]}
                    >
                        <InputNumber
                            style={{ width: 200 }}
                            min={0}
                            max={9999999999999}
                            formatter={(value) => { return !isNaN(value) ? String(value).replace(/^0(0+)|[^\d]+/g, '') : '' }}
                            parser={(value) => { return !isNaN(value) ? String(value).replace(/^0(0+)|[^\d]+/g, '') : '' }}
                            disabled={edited[data.indexOf(element)]}
                        />
                    </Form.Item>

                    <Form.Item
                        label="持续时长(S)"
                        name="duration"
                        initialValue={element.duration}
                        rules={[
                            {
                                required: true,
                                message: '请输入创意持续时长!',
                            },
                        ]}
                    >
                        <InputNumber
                            style={{ width: 200 }}
                            min={0}
                            max={9999999999999}
                            disabled={edited[data.indexOf(element)]}
                        />
                    </Form.Item>

                    <Form.Item
                        label="广告创意地址"
                        name="url"
                        initialValue={element.url}
                        rules={[
                            {
                                required: true,
                                message: '请输入广告创意地址!',
                            },
                        ]}
                    >
                        <Input disabled={edited[data.indexOf(element)]}
                            style={{ width: 200 }}
                            defaultValue={element.url} />
                    </Form.Item>

                    <Form.Item
                        label="广告创意具体信息"
                        name="adContents"
                        initialValue={element.adContents}
                        rules={[
                            {
                                required: true,
                                message: '请输入广告创意具体信息!',
                            },
                        ]}
                    >
                        <Input disabled={edited[data.indexOf(element)]}
                            style={{ width: 200 }}
                            defaultValue={element.adContents}
                            placeholder="广告创意具体信息" />
                    </Form.Item>

                    <Form.Item
                        label="创意创建时间"
                        name="createTime"
                        initialValue={moment(element.createTime, dateFormat)}
                    >
                        <DatePicker disabled={true} showTime style={{ width: 200 }} defaultValue={moment(element.createTime, dateFormat)} />
                    </Form.Item>
                    <Form.Item
                        label="创意更新时间"
                        name="updateTime"
                        initialValue={moment(element.updateTime, dateFormat)}
                    >
                        <DatePicker disabled={true} showTime style={{ width: 200 }} defaultValue={moment(element.updateTime, dateFormat)} />
                    </Form.Item>
                    <Form.Item
                        label="审核状态"
                        name="auditStatus"
                        valuePropName='checked'
                    >
                        <Switch className={element.id}
                            checkedChildren="审核通过"
                            unCheckedChildren="未审核"
                            defaultChecked={element.auditStatus === 1}
                            disabled={edited[data.indexOf(element)]}
                            onChange={(checked) => {
                                if (checked === true) {
                                    element.auditStatus = 1;
                                } else {
                                    element.auditStatus = 0;
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