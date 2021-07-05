import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter, Route, Switch } from 'react-router-dom'
// import { Provider } from 'react-redux'
import Loadable from 'react-loadable'
import 'antd/dist/antd.css'
import reportWebVitals from './reportWebVitals';
import Index from './pages/Index/Index';
import Admin from './pages/Admin/Admin';

// 懒加载加载框
import PageLoading from './components/PageLoading/PageLoading'

// 多个页面加载
// const Index = Loadable({
//   loading: PageLoading,
//   timeout: 1000,
//   loader: () => import('./pages/Index/Index')
// })

// const Admin = Loadable({
//   loading: PageLoading,
//   timeout: 1000,
//   loader: () => import('./pages/Admin/Admin')
// })
class App extends React.Component {
  render () {
    return (
        <BrowserRouter>
        {/* <HashRouter> */}
          <Switch>
          <Route path='/' exact component={Index} />
            <Route path='/admin' exact component={Admin} />
          </Switch>
        {/* </HashRouter> */}
        </BrowserRouter>
    )
  }
}

ReactDOM.render(
    <App />,
  document.getElementById('root')
);

reportWebVitals();
