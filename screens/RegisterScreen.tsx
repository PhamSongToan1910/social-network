import React, { useState } from 'react';
import { NavigationProp, useNavigation } from '@react-navigation/native';
import {
  View,
  Text,
  Image,
  TextInput,
  TouchableOpacity,
  StyleSheet,
  Alert
} from 'react-native';

const RegisterScreen = () => {
  const [fullName, setFullName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  const navigation: NavigationProp<RootStackParamList> = useNavigation();

  const handleRegister = () => {
    if (password !== confirmPassword) {
      Alert.alert('Lỗi', 'Mật khẩu và xác nhận mật khẩu không khớp');
      return;
    }
    
    console.log('Họ tên:', fullName);
    console.log('Email:', email);
    console.log('Mật khẩu:', password);
  };
  

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Đăng ký</Text>
      
      <View style={{ justifyContent: 'center', alignItems: 'center', marginLeft:10, marginBottom: 10}} >
      <Image
        style={{height:150, width:150}}
        source={require("../assets/images/register3.jpg")}
      />
      </View>

      <TextInput
        style={styles.input}
        placeholder="Họ tên"
        placeholderTextColor="#888"
        value={fullName}
        onChangeText={setFullName}
      />
      
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
        secureTextEntry={true} // Ẩn mật khẩu
        autoCapitalize="none"
      />
      
      <TextInput
        style={styles.input}
        placeholder="Xác nhận mật khẩu"
        placeholderTextColor="#888"
        value={confirmPassword}
        onChangeText={setConfirmPassword}
        secureTextEntry={true} // Ẩn mật khẩu
        autoCapitalize="none"
      />
      
      <TouchableOpacity style={styles.button} onPress={handleRegister}>
        <Text style={styles.buttonText}>Đăng ký</Text>
      </TouchableOpacity>
      <View style={styles.loginContainer}>
        <Text style={styles.loginText}>Đã có tài khoản?</Text>
        <TouchableOpacity onPress={() => navigation.navigate('Login')}>
          <Text style={styles.loginLink}>Đăng nhập</Text>
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
    // marginBottom: 24,
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
  loginContainer: {
    flexDirection: 'row',
    justifyContent: 'center',
  },
  loginText: {
    fontSize: 16,
  },
  loginLink: {
    fontSize: 16,
    color: '#007BFF',
    marginLeft: 4,
  },
});

export default RegisterScreen;
