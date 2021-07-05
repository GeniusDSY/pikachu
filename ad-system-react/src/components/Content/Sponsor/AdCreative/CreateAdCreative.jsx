import React from 'react'
import { Layout, Form, Select, InputNumber, Button, Input } from 'antd'
import { withRouter } from 'react-router'
import { successMsg, failMsg } from '../../../UtilFunction'
import Axios from 'axios'

const { Content } = Layout;
const { Option } = Select;

class CreateAdCreative extends React.PureComponent {

    constructor(props) {
        super(props);
        this.state = {
            types: ['图片', '视频', '文本'],
            materialTypes: {
                图片: ['JPG', 'BMP'],
                视频: ['MP4', 'AVI'],
                文本: ['TXT']
            },
            unitMsg: [],
            displayType: '图片',
            displayMaterialType: 'JPG',
            creativeDTO: {
                name: "", //创意名称
                type: 0, // 创意类型
                materialType: 0, //素材类型
                height: 0, //高度
                width: 0, //宽度
                size: 0, //大小
                duration: 0, //时长
                userId: 0, //创意的所有者（创建人）
                url: "", //创意的访问地址
                adContents: ""
            },
            creativeUnitDTO: {
            }
        }
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
                name: props.name,
                type: this.getIndex(this.state.types, this.state.displayType) + 1,
                materialType: this.getMaterialTypeIndex(this.getIndex(this.state.types, this.state.displayType)),
                height: props.height,
                width: props.width, //宽度
                size: props.size, //大小
                duration: props.duration, //时长
                adContents: props.adContents,
                userId: 15, //创意的所有者（创建人）
                url: props.url, //创意的访问地址
            }
        })
        this.createCreative(props);
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
                unitMsg: response.data.data
            })
        }).catch(function (error) {
            console.log(error);
        });
    }

    createCreative(props) {
        let that = this;
        Axios({
            method: "POST",
            url: "http://localhost:8080/pikachu/ad-sponsor/creative/create",
            data: this.state.creativeDTO
        }).then(function (response) {
            console.log(response)
            that.createCreativeUnit(response, props.unitId);
            successMsg(response.data.message);
        }).catch(function (error) {
            failMsg();
        });
    }

    createCreativeUnit(response, unitId) {
        this.setState({
            creativeUnitDTO: {
                creativeUnitItems: [
                    {
                        creativeId: response.data.data.id,
                        unitId: parseInt(unitId.replace(/([.][^/]+)$/, "")),
                    }
                ]
            }
        })
        Axios({
            method: "POST",
            url: "http://localhost:8080/pikachu/ad-sponsor/adUnit/create/creativeUnit",
            data: this.state.creativeUnitDTO
        }).then(function (response) {
            console.log(response)
            successMsg(response.data.message);
        }).catch(function (error) {
            failMsg();
        });
    }

    onFinishFailed(props) {
        console.log(props)
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
                            label="广告单元Id"
                            name="unitId"
                            rules={[
                                {
                                    required: true,
                                    message: '请输入广告单元Id!',
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
                            >
                                {
                                    this.state.unitMsg.map((unit, index) => {
                                        return (<Option key={index} value={unit}>{unit}</Option>)
                                    })
                                }
                            </Select>
                        </Form.Item>

                        <Form.Item
                            label="广告创意名称"
                            name="name"
                            rules={[
                                {
                                    required: true,
                                    message: '请输入广告创意名称!',
                                },
                            ]}
                        >
                            <Input style={{ width: 400 }} placeholder="给广告创意起一个有意思的名称吧！" />
                        </Form.Item>
                        <Form.Item label="创意类型"
                            name="type"
                            initialValue={this.state.displayType}
                            rules={[
                                {
                                    required: true,
                                    message: '请选择创意类型!',
                                },
                            ]}>
                            <Select defaultValue={this.state.displayType}
                                style={{ width: 200 }}
                                onChange={this.handleTypeChange.bind(this)}>
                                {this.state.types.map(type => (
                                    <Option key={type}>{type}</Option>
                                ))}
                            </Select>
                            <Select style={{ width: 200 }}
                                value={this.state.displayMaterialType}
                                onChange={this.handleMaterialTypeChange.bind(this)}>
                                {this.state.materialTypes[this.state.displayType].map(materialType => (
                                    <Option key={materialType}>{materialType}</Option>
                                ))}
                            </Select>

                        </Form.Item>

                        <Form.Item
                            label="高度(PX)"
                            name="height"
                            rules={[
                                {
                                    required: true,
                                    message: '请输入创意高度!',
                                },
                            ]}
                        >
                            <InputNumber
                                style={{ width: 400 }}
                                min={0}
                                max={9999999999999}
                                formatter={(value) => { return !isNaN(value) ? String(value).replace(/^0(0+)|[^\d]+/g, '') : '' }}
                                parser={(value) => { return !isNaN(value) ? String(value).replace(/^0(0+)|[^\d]+/g, '') : '' }}
                                placeholder="您的创意有多高呢(整数哦)？？"
                            />
                        </Form.Item>

                        <Form.Item
                            label="宽度(PX)"
                            name="width"
                            rules={[
                                {
                                    required: true,
                                    message: '请输入创意宽度!',
                                },
                            ]}
                        >
                            <InputNumber
                                style={{ width: 400 }}
                                min={0}
                                max={9999999999999}
                                formatter={(value) => { return !isNaN(value) ? String(value).replace(/^0(0+)|[^\d]+/g, '') : '' }}
                                parser={(value) => { return !isNaN(value) ? String(value).replace(/^0(0+)|[^\d]+/g, '') : '' }}
                                placeholder="您的创意有多宽呢(整数哦)？？"
                            />
                        </Form.Item>

                        <Form.Item
                            label="大小(M)"
                            name="size"
                            rules={[
                                {
                                    required: true,
                                    message: '请输入创意大小!',
                                },
                            ]}
                        >
                            <InputNumber
                                style={{ width: 400 }}
                                min={0}
                                max={9999999999999}
                                formatter={(value) => { return !isNaN(value) ? String(value).replace(/^0(0+)|[^\d]+/g, '') : '' }}
                                parser={(value) => { return !isNaN(value) ? String(value).replace(/^0(0+)|[^\d]+/g, '') : '' }}
                                placeholder="您的创意占多大存储呢(整数哦)？？"
                            />
                        </Form.Item>

                        <Form.Item
                            label="持续时长(S)"
                            name="duration"
                            rules={[
                                {
                                    required: true,
                                    message: '请输入创意持续时长!',
                                },
                            ]}
                        >
                            <InputNumber
                                style={{ width: 400 }}
                                min={0}
                                max={9999999999999}
                                placeholder="您的创意要持续多久呢(整数哦)？？"
                            />
                        </Form.Item>

                        <Form.Item
                            label="广告创意地址"
                            name="url"
                            rules={[
                                {
                                    required: true,
                                    message: '请输入广告创意地址!',
                                },
                            ]}
                        >
                            <Input style={{ width: 400 }} placeholder="把你最喜爱的广告创意地址贴在这吧！" />
                        </Form.Item>

                        <Form.Item
                            label="广告创意具体信息"
                            name="adContents"
                            rules={[
                                {
                                    required: true,
                                    message: '请输入广告创意具体信息!',
                                },
                            ]}
                        >
                            <Input style={{ width: 400 }} placeholder="广告创意具体信息" />
                        </Form.Item>

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

export default withRouter(CreateAdCreative)