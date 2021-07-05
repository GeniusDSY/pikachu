import React from 'react'
import { Layout, Form, Button, Select } from 'antd';
import { MinusCircleOutlined, PlusOutlined } from '@ant-design/icons';
import { withRouter } from 'react-router';
import Axios from 'axios';
import getProvince from '../../../assets/province.json';
import getCity from './../../../assets/city.json';
import { successMsg, failMsg } from '../../UtilFunction'

const { Content } = Layout;
const { Option } = Select;

class AdDistrictContent extends React.PureComponent {

    constructor(props) {
        super(props);
        this.state = {
            planMsg: [],
            unitDistricts: [],
            adUnitDistrictDTO: {},
            province: getProvince[0].name, // 当前省
            cities: getCity[getProvince[0].id], // 当前省下市
            cityValue: getCity[getProvince[0].id][0].name, // 当前市
        }
    }

    componentDidMount() {
        let that = this;
        let unitDistricts = [];
        unitDistricts.push({ province: getProvince[0].name, city: getCity[getProvince[0].id][0].name })
        this.setState({
            unitDistricts: unitDistricts
        })
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

    createUnitDistrict() {
        Axios({
            method: "POST",
            url: "http://localhost:8080/pikachu/ad-sponsor/adUnit/create/unitDistrict",
            data: this.state.adUnitDistrictDTO
        }).then(function (response) {
            successMsg(response.data.message);
        }).catch(function (error) {
        })
    }

    onFinish() {
        let adUnitDistrictDTO = this.state.adUnitDistrictDTO;
        adUnitDistrictDTO.unitDistricts = this.state.unitDistricts;
        console.log(adUnitDistrictDTO)
        this.setState({
            adUnitDistrictDTO: adUnitDistrictDTO
        })
        this.createUnitDistrict()
    }

    onFinishFailed() {
    }

    unitIdChange(value, index) {
        let unitDistricts = this.state.unitDistricts;
        if (unitDistricts.length === index) {
            unitDistricts.push({
                unitId: parseInt(value.replace(/([.][^/]+)$/, "")),
                province: this.state.province,
                city: this.state.cityValue
            })
        } else {
            unitDistricts[index].unitId = parseInt(value.replace(/([.][^/]+)$/, ""))
        }
        this.setState({
            unitDistricts: unitDistricts
        })
    }
    // 省下拉框改变
    changeProvince(e, i, index) {
        let unitDistricts = this.state.unitDistricts;
        if (unitDistricts.length === index) {
            unitDistricts.push({ province: e, city: getCity[i.key][0].name})
        } else {
            unitDistricts[index].province = e;
            unitDistricts[index].city = getCity[i.key][0].name;
        }
        this.setState({
            province: e,
            cities: getCity[i.key],
            cityValue: getCity[i.key][0].name,
            provinceCode: i.key,
            unitDistricts: unitDistricts
        }, () => {
            console.log({ province: e, cities: getCity[i.key], cityValue: getCity[i.key][0].name, provinceCode: i.key })
        })
    }
    // 市下拉框改变
    changeCity = (e, i, index) => {
        let unitDistricts = this.state.unitDistricts;
        if (unitDistricts.length === index) {
            unitDistricts.push({ city: e })
        } else {
            unitDistricts[index].city = e
        }
        this.setState({
            cityValue: e,
            cityCode: i.key,
            unitDistricts: unitDistricts
        }, () => {
            console.log({ cityValue: e, cityCode: i.key })
        })
    }

    render() {

        const { province, cities, cityValue } = this.state;

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
                            name="unitDistrict"
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
                                                        message: '请选择区域影响因素'
                                                    }
                                                ]}
                                                label={"区域限制因素"}
                                            >
                                                <Select
                                                    onChange={(e, i) => this.changeProvince(e, i, index)}
                                                    defaultValue={this.state.province}
                                                    style={{ width: 200 }}
                                                >
                                                    {(getProvince.length > 0) ? getProvince.map(province => (
                                                        <Option key={province.id} value={province.name} >{province.name}</Option>
                                                    )) : ''}
                                                </Select>
                                                <Select
                                                    onChange={(e, i) => this.changeCity(e, i, index)}
                                                    defaultValue={this.state.cityValue}
                                                    style={{ width: 200 }}
                                                >
                                                    {(cities.length > 0) ? cities.map(city => (
                                                        <Option key={city.id} value={city.name}>{city.name}</Option>
                                                    )) : ''}
                                                </Select>
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
                                            增加新的地域标签
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

export default withRouter(AdDistrictContent)