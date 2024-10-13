// HomeScreen.tsx
import React, { useEffect, useState } from 'react';
import {
  View,
  Image,
  Text,
  FlatList,
  TouchableOpacity,
  StyleSheet,
  StatusBar,
} from 'react-native';
import { NavigationProp, useNavigation } from '@react-navigation/native';
import { SafeAreaView } from 'react-native-safe-area-context';
import AntDesign from '@expo/vector-icons/AntDesign';
import PostItem from '../components/PostItem';
import { fakePosts } from '../data/fakeData';
import FontAwesome from '@expo/vector-icons/FontAwesome';
import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';

interface Post {
  id: String,
  userID: String,
  content: String,
  image: String,
  status: Number,
  createAt: String,
  typeOfPost: Number,
  countOfReacts: Number,
  countOfComments: Number,
  countOfShares: Number,
  reaction: Number
}

const HomeScreen = () => {
  const navigation = useNavigation<NavigationProp<RootStackParamList>>();
  const [posts, setPosts] = useState<Post[]>([]);
  useEffect(() => {
    const fetchData = async () => {
      try {
        const page = 1;
        const token = await AsyncStorage.getItem('@userToken');
        console.log(token);  
        const response = await axios.get(
          `http://192.130.38.116:8080/api/social-network/post/get-all-posts?page=${page}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
              'Content-Type': 'application/json',
            },
          }
        );
        
        console.log('Phản hồi API:', response.data);
        setPosts(response.data.data);
      } catch (error) {
        console.error('Lỗi khi gọi API:', error);
      }
    };

    fetchData();
  }, []);

  return (
    <SafeAreaView style={styles.safeArea}>
      {/* Điều chỉnh StatusBar để tránh header bị che */}
      <StatusBar barStyle="dark-content" backgroundColor="transparent" translucent />

      {/* Header luôn hiển thị phía trên */}
      <View style={styles.header}>
        <Text style={styles.titleHeader}>Instagram</Text>
        <View style={styles.headerIcons}>
          <TouchableOpacity style={styles.iconButton} onPress={() => navigation.navigate('Notifications')} >
            <FontAwesome name="bell-o" size={24} color="black" />
          </TouchableOpacity>
          <TouchableOpacity style={styles.iconButton}>            
            <AntDesign name="message1" size={24} color="black" />
          </TouchableOpacity>
        </View>
      </View>

      {/* Nội dung cuộn */}
      <FlatList
        data={posts}
        keyExtractor={(item) => item.id}
        renderItem={({ item }) => <PostItem post={item} />}
        // contentContainerStyle={styles.flatListContent} 
      />
      
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: '#f5f5f5',
  },
  header: {
    height: 50,
    // backgroundColor: 'yellow',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 12,
  },
  titleHeader: {
    fontSize: 18,
    textTransform: 'uppercase',
    fontWeight: 'bold',
  },
  headerIcons: {
    flexDirection: 'row',
  },
  iconButton: {
    padding: 10,
    borderRadius: 5,
  },
  profileImage: {
    height: 50,
    width: 50,
    borderRadius: 25,
    marginLeft: 10,
  },
  flatListContent: {
    paddingTop: 20, 
  },
});

export default HomeScreen;
