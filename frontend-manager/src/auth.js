import VueJwtDecode from 'vue-jwt-decode';

export default {
    user: {
        authenticated: false,
        roles: '',
        username: ''
    },
    authenticated: async function() {
        const token = localStorage.getItem('jwtToken');
        // if there is a token, get the user roles, and try to authinticate
        if (token) {
            this.user.roles = VueJwtDecode.decode(token).roles;
            console.log('roles ' + this.user.roles);
            // Set the Authorization header with the token value
            const headers = {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token}`
            };
            await fetch("http://localhost:8080/auth/authenticate", { headers })
                .then((response) => response.json())
                .then((data) => {
                    this.user.authenticated = data;
                    console.log(data);
                })
                .catch(err => console.log(err.message));
            return this.user.authenticated; //returns a boolean value
        } else
        // there is no token, we cannot authinticate
            return false;
    },

    hasARoleOf: function(roles) {
        if (this.user.roles)
            return this.user.roles == roles;
    },

    logout: function() {
        localStorage.removeItem('jwtToken');
        this.user = { roles: "", username: "", authenticated: false };
    },
}