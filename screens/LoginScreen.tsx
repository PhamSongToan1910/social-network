import React, { useState } from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

import {
  View,
  Image,
  ImageBackground,
  Text,
  TextInput,
  TouchableOpacity,
  StyleSheet
} from 'react-native';
import { NavigationProp, useNavigation } from '@react-navigation/native';

const LoginScreen = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [rememberMe, setRememberMe] = useState(false);
  
  const navigation: NavigationProp<RootStackParamList> = useNavigation();
  
  const handleLogin = () => {
    console.log('Email:', email);
    console.log('Password:', password);
    console.log('Remember Me:', rememberMe);
  };

  

  return (

    <View style={styles.container}>
      <Text style={styles.title}>Đăng nhập</Text>
      
      <View style={{ justifyContent: 'center', alignItems: 'center'}} >
      <Image
        style={{height:200, width:150}}
        source={require("../assets/images/login.png")}
      />
      </View> 
      
      <TextInput
        style={styles.input}
        placeholder="Email"
        placeholderTextColor="#888"
        value={email}
        onChangeText={setEmail}
        keyboardType="email-address"
        autoCapitalize="none"
      />
      
      <TextInput
        style={styles.input}
        placeholder="Mật khẩu"
        placeholderTextColor="#888"
        value={password}
        onChangeText={setPassword}
        secureTextEntry={true}
        autoCapitalize="none"
      />

      
      <View style={styles.rememberForgotContainer}>
        <View style={styles.rememberMeContainer}>
          <TouchableOpacity
            style={styles.checkbox}
            onPress={() => setRememberMe(!rememberMe)}
          >
            {rememberMe ? <Text style={styles.checkboxText}>✔</Text> : null}
          </TouchableOpacity>
          <Text style={styles.rememberMeText}>Ghi nhớ mật khẩu</Text>
        </View>
        
        <TouchableOpacity onPress={()=> navigation.navigate('Forgot')}>
          <Text style={styles.forgotPasswordText}>Forgot Password?</Text>
        </TouchableOpacity>
      </View>

      <TouchableOpacity style={styles.button} onPress={()=> navigation.navigate('Home')}>
        <Text style={styles.buttonText}>Đăng nhập</Text>
      </TouchableOpacity>
      
        <View style={styles.registerContainer}>
          <Text style={styles.registerText}>Chưa có tài khoản?</Text>
          <TouchableOpacity onPress={()=> navigation.navigate('Register')} >
            <Text style={styles.registerLink}>Đăng ký</Text>
          </TouchableOpacity>
        </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    paddingHorizontal: 24,
    backgroundColor: '#f5f5f5',
  },
  title: {
    fontSize: 32,
    fontWeight: 'bold',
    // marginBottom: 12,
    textAlign: 'center',
  },
  input: {
    height: 50,
    borderColor: '#ddd',
    borderWidth: 1,
    borderRadius: 8,
    paddingHorizontal: 16,
    marginBottom: 16,
    backgroundColor: '#fff',
  },
  rememberForgotContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 24,
  },
  rememberMeContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  checkbox: {
    width: 16,
    height: 16,
    borderWidth: 1,
    borderColor: '#888',
    justifyContent: 'center',
    alignItems: 'center',
    marginRight: 8, 
  },
  checkboxText: {
    fontSize: 12,
    color: '#007BFF',
  },
  rememberMeText: {
    fontSize: 16,
  },
  forgotPasswordText: {
    color: '#007BFF',
    fontSize: 16,
  },
  button: {
    backgroundColor: '#007BFF',
    paddingVertical: 12,
    borderRadius: 8,
    alignItems: 'center',
    marginBottom: 24,
  },
  buttonText: {
    color: '#fff',
    fontSize: 18,
  },
  registerContainer: {
    flexDirection: 'row',
    justifyContent: 'center',
  },
  registerText: {
    fontSize: 16,
  },
  registerLink: {
    fontSize: 16,
    color: '#007BFF',
    marginLeft: 4,
  },
});

export default LoginScreen;
