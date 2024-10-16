import React, { useState } from 'react';
import {
  View,
  Text,
  TextInput,
  Image,
  TouchableOpacity,
  StyleSheet,
  SafeAreaView,
  Platform,
  StatusBar,
  ScrollView,
  Modal,
} from 'react-native';
import Icon from 'react-native-vector-icons/Ionicons';
import MaterialIcon from 'react-native-vector-icons/MaterialIcons';
import { NavigationProp, useNavigation } from '@react-navigation/native';
import { launchImageLibrary } from 'react-native-image-picker';
import { PermissionsAndroid } from 'react-native';
import axios from 'axios';

type StatusOption = 1 | 2 | 3;

const CreatePostScreen = () => {
  const [caption, setCaption] = useState('');
  const [selectedImage, setSelectedImage] = useState<string | null>(null);
  const [status, setStatus] = useState<StatusOption>(1);
  const [showStatusModal, setShowStatusModal] = useState(false);
  const navigation: NavigationProp<RootStackParamList> = useNavigation();


  const handleSelectImage = () => {
    const requestCameraPermission = async () => {
      try {
        const granted = await PermissionsAndroid.request(PermissionsAndroid.PERMISSIONS.CAMERA);
        if (granted === PermissionsAndroid.RESULTS.GRANTED) {
          console.log("Camera permission given");
          const result:any = await launchImageLibrary({mediaType:'photo',})
          setSelectedImage(result.assets[0].uri);
        } else {
          console.log("Camera permission denied");
        }
      } catch (err) {
        console.warn(err);
      }
    };
  };

  const handlePost = async () => {
    const userID = '12345';
  
    const postData = {
      userID,
      caption,
      image: selectedImage,
      status,
      typeOfPost: 1,
    };
  
    try {
      const response = await axios.post('https://your-api-endpoint.com/posts', postData);
      console.log('Post created successfully:', response.data);
      navigation.navigate('Home')
    } catch (error) {
      console.error('Lỗi khi gọi API đăng nhập:', error);
    }
  };
  

  const getStatusIcon = (statusOption: StatusOption) => {
    switch (statusOption) {
      case 1:
        return <MaterialIcon name="public" size={24} color="#000" />;
      case 2:
        return <MaterialIcon name="people" size={24} color="#000" />;
      case 3:
        return <MaterialIcon name="lock" size={24} color="#000" />;
    }
  };

  const getStatusText = (statusOption: StatusOption) => {
    switch (statusOption) {
      case 1:
        return 'Công khai';
      case 2:
        return 'Bạn bè';
      case 3:
        return 'Chỉ riêng mình tôi';
    }
  };

  return (
    <SafeAreaView style={styles.safeArea}>
      <ScrollView style={styles.container}>
        <View style={styles.header}>
          <TouchableOpacity onPress={() => navigation.goBack()}>
            <Icon name="close-outline" size={30} color="#000" />
          </TouchableOpacity>
          <Text style={styles.headerTitle}>Tạo bài viết mới</Text>
          <TouchableOpacity onPress={handlePost}>
            <Text style={styles.postButton}>Đăng</Text>
          </TouchableOpacity>
        </View>

        <View style={styles.imageContainer}>
          {selectedImage ? (
            <Image source={{ uri: selectedImage }} style={styles.selectedImage} />
          ) : (
            <TouchableOpacity style={styles.imagePlaceholder} onPress={handleSelectImage}>
              <Icon name="image-outline" size={50} color="#999" />
              <Text style={styles.imagePlaceholderText}>Chọn ảnh</Text>
            </TouchableOpacity>
          )}
        </View>

        <TextInput
          style={styles.captionInput}
          placeholder="Viết chú thích..."
          placeholderTextColor="#999"
          multiline
          value={caption}
          onChangeText={setCaption}
        />

        <View style={styles.optionsContainer}>
          <TouchableOpacity style={styles.option} onPress={() => setShowStatusModal(true)}>
            {getStatusIcon(status)}
            <Text style={styles.optionText}>{getStatusText(status)}</Text>
          </TouchableOpacity>
        </View>
      </ScrollView>

      <Modal
        visible={showStatusModal}
        transparent={true}
        animationType="slide"
        onRequestClose={() => setShowStatusModal(false)}
      >
        <View style={styles.modalContainer}>
          <View style={styles.modalContent}>
            <Text style={styles.modalTitle}>Chọn đối tượng</Text>
            {([1, 2, 3] as StatusOption[]).map((option) => (
              <TouchableOpacity
                key={option}
                style={styles.modalOption}
                onPress={() => {
                  setStatus(option);
                  setShowStatusModal(false);
                }}
              >
                {getStatusIcon(option)}
                <Text style={styles.modalOptionText}>{getStatusText(option)}</Text>
              </TouchableOpacity>
            ))}
          </View>
        </View>
      </Modal>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: '#fff',
    paddingTop: Platform.OS === 'android' ? StatusBar.currentHeight : 0,
  },
  container: {
    flex: 1,
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: 15,
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },
  headerTitle: {
    fontSize: 18,
    fontWeight: 'bold',
  },
  postButton: {
    color: '#3897f0',
    fontSize: 16,
    fontWeight: 'bold',
  },
  imageContainer: {
    aspectRatio: 1,
    width: '100%',
    backgroundColor: '#f0f0f0',
    justifyContent: 'center',
    alignItems: 'center',
  },
  selectedImage: {
    width: '100%',
    height: '100%',
  },
  imagePlaceholder: {
    justifyContent: 'center',
    alignItems: 'center',
  },
  imagePlaceholderText: {
    marginTop: 10,
    fontSize: 16,
    color: '#999',
  },
  captionInput: {
    padding: 15,
    fontSize: 16,
  },
  optionsContainer: {
    padding: 15,
  },
  option: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingVertical: 10,
  },
  optionText: {
    marginLeft: 10,
    fontSize: 16,
  },
  modalContainer: {
    flex: 1,
    justifyContent: 'flex-end',
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
  },
  modalContent: {
    backgroundColor: 'white',
    borderTopLeftRadius: 20,
    borderTopRightRadius: 20,
    padding: 20,
  },
  modalTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    marginBottom: 15,
  },
  modalOption: {
    flexDirection: 'row',
    alignItems: 'center',
    paddingVertical: 15,
    borderBottomWidth: 1,
    borderBottomColor: '#eee',
  },
  modalOptionText: {
    fontSize: 16,
    marginLeft: 15,
  },
});

export default CreatePostScreen;