import React, { useState } from 'react';
import { View, Text, TouchableOpacity, Modal, StyleSheet, TextInput, Image, Alert } from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome';
import { NavigationProp, useNavigation } from '@react-navigation/native';
import axios from 'axios';

interface ShareButtonProps {
  userAvatar: string;
  userName: string;
  postID: string; 
}

const ShareButton: React.FC<ShareButtonProps> = ({ userAvatar, userName, postID }) => {
  const [modalVisible, setModalVisible] = useState(false);
  const [shareContent, setShareContent] = useState('');

  const navigation: NavigationProp<RootStackParamList> = useNavigation();

  const handleShare = async () => {

    const postData = {
      
      typeOfPost: 2,
    };
    try {
      const response = await axios.post('https://your-api-endpoint.com/posts',postData );
      console.log('Post created successfully:', response.data);
      navigation.navigate('Home')
    } catch (error) {
      console.error('Lỗi khi gọi API đăng nhập:', error);
    }
  };

  return (
    <>
      <TouchableOpacity onPress={() => setModalVisible(true)} style={styles.shareButton}>
        <Icon name="paper-plane" size={24} color="#000" />
        <Text style={styles.shareButtonText}>Share</Text>
      </TouchableOpacity>

      <Modal
        animationType="slide"
        transparent={true}
        visible={modalVisible}
        onRequestClose={() => setModalVisible(false)}
      >
        <View style={styles.centeredView}>
          <View style={styles.modalView}>
            <View style={styles.modalHeader}>
              <TouchableOpacity onPress={() => setModalVisible(false)}>
                <Icon name="close" size={24} color="#000" />
              </TouchableOpacity>
              <Text style={styles.modalTitle}>Tạo bài viết</Text>
              <TouchableOpacity onPress={handleShare} style={styles.shareNowButton}>
                <Text style={styles.shareNowButtonText}>Chia sẻ ngay</Text>
              </TouchableOpacity>
            </View>

            <View style={styles.userInfo}>
              <Image source={{ uri: userAvatar }} style={styles.avatar} />
              <Text style={styles.userName}>{userName}</Text>
            </View>

            <TextInput
              style={styles.input}
              multiline
              placeholder="Bạn đang nghĩ gì?"
              value={shareContent}
              onChangeText={setShareContent}
            />
          </View>
        </View>
      </Modal>
    </>
  );
};

const styles = StyleSheet.create({
  shareButton: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 10,
  },
  shareButtonText: {
    marginLeft: 5,
  },
  centeredView: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
  },
  modalView: {
    backgroundColor: 'white',
    borderRadius: 20,
    padding: 20,
    width: '90%',
    maxHeight: '80%',
  },
  modalHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 20,
  },
  modalTitle: {
    fontSize: 18,
    fontWeight: 'bold',
  },
  shareNowButton: {
    backgroundColor: '#1877F2',
    paddingHorizontal: 15,
    paddingVertical: 8,
    borderRadius: 5,
  },
  shareNowButtonText: {
    color: '#FFFFFF',
    fontWeight: 'bold',
  },
  userInfo: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 15,
  },
  avatar: {
    width: 40,
    height: 40,
    borderRadius: 20,
    marginRight: 10,
  },
  userName: {
    fontWeight: 'bold',
  },
  input: {
    fontSize: 16,
    minHeight: 100,
    textAlignVertical: 'top',
  },
});

export default ShareButton;