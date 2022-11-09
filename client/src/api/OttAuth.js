import axios from 'axios'

export const postAuth = (body,token) => {
    return axios({
        url: '/api/v1/ottAuth',
        method: 'POST',
        headers: {
            'Content-Type' : 'application/json',
            Authorization: `Bearer ${token}`,
        }, 
        data: JSON.stringify(body)
        
    })
}