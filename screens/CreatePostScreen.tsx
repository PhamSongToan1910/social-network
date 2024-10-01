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
} from 'react-native';
import Icon from 'react-native-vector-icons/Ionicons';
import { NavigationProp, useNavigation } from '@react-navigation/native';

const CreatePostScreen = () => {
  const [caption, setCaption] = useState('');
  const [selectedImage, setSelectedImage] = useState<string | null>(null);
  const navigation: NavigationProp<RootStackParamList> = useNavigation();

  const handleSelectImage = () => {
    // Implement image selection logic here
    // For now, we'll just set a dummy image
    setSelectedImage('https://via.placeholder.com/300');
  };

  const handlePost = () => {
    // Implement post creation logic here
    console.log('Posting:', { caption, image: selectedImage });
    // After posting, navigate back to the feed or profile
    navigation.goBack();
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
          <TouchableOpacity style={styles.option}>
            <Icon name="location-outline" size={24} color="#000" />
            <Text style={styles.optionText}>Thêm vị trí</Text>
          </TouchableOpacity>
          <TouchableOpacity style={styles.option}>
            <Icon name="people-outline" size={24} color="#000" />
            <Text style={styles.optionText}>Gắn thẻ người khác</Text>
          </TouchableOpacity>
        </View>
      </ScrollView>
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
});

export default CreatePostScreen;