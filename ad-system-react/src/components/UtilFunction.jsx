import {message} from 'antd'

const key = 'updatable';

// 成功提示
export function successMsg() {
    message.loading({ content: 'Loading...', key });
    setTimeout(() => {
        message.success({ content: '成功', key, duration: 2 });
    }, 1000);
}

// 失败提示
export function failMsg() {
    message.loading({ content: 'Loading...', key });
    setTimeout(() => {
        message.error({ content: '失败！', key, duration: 2 });
    }, 1000);
}