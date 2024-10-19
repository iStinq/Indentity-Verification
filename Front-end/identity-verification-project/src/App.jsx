import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Login } from './Pages/Login/login'
import { Register } from './Pages/Register/register';
import { Form } from './Pages/Form/form';

function App() {

  return (
    <>
      <Router>
        <Routes>
          {sessionStorage.getItem("isAuth") !== "true" ? <Route path='/' element={<Login />}/> : <Route path='/' element={<Form />}/>}
          <Route path='/login' element={<Login />}/>
          <Route path='/register' element={<Register />}/>
          <Route path='/verify' element={<Form />}/>
        </Routes>
      </Router>
    </>
  )
}

export default App
