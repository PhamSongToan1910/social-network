// HomeScreen.tsx
import React, { useEffect } from 'react';
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

const HomeScreen = () => {
  const navigation = useNavigation<NavigationProp<RootStackParamList>>();
  useEffect(() => {
    const fetchData = async () => {
      try {
        const page = 1;
        const response = await axios.post('http://localhost:8080/api/social-network/post/get-all-posts', {
          page
        });
        console.log('API response:', response.data);
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
        data={fakePosts}
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
