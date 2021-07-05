// 获取地理位置信息
import getProvince from '../../../assets/province.json';
import getCity from './../../../assets/city.json';

// const options = [
//     {
//       value: 'zhejiang',
//       label: 'Zhejiang',
//       children: [
//         {
//           value: 'Hangzhou',
//           label: 'Hangzhou',
//         },
//       ],
//     }
//   ];

const Options = getProvince.map(province => {
    return {
        value: province.name,
        label: province.name,
        children: getCity[province.id].map( city => {
            return {
                value: city.name,
                label: city.name
            }
        })
    }
})


export default Options;