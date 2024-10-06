import React, { useState } from 'react';
import {
  View,
  Text,
  Image,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
  FlatList,
  Dimensions,
  Modal,
  SafeAreaView,
  Platform,
  StatusBar,
} from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
// import { RootStackParamList } from '../types/route';

const { width } = Dimensions.get('window');

// Giả lập dữ liệu người dùng
const userData = {
  id: '1',
  name: 'Nguyễn Văn A',
  username: 'nguyenvana',
  avatar: 'https://example.com/avatar.jpg',
  bio: 'Photographer | Traveler | Food Lover',
  postsCount: 42,
  followersCount: 1500,
  followingCount: 500,
};

// Giả lập dữ liệu bài đăng
const postData = Array(15).fill(null).map((_, index) => ({
  id: index.toString(),
  // imageUrl: `https://picsum.photos/500/500?random=${index}`,
}));

type ProfileScreenNavigationProp = StackNavigationProp<RootStackParamList, 'Profile'>;

const ProfileScreen = () => {
  const [showMenu, setShowMenu] = useState(false);
  const navigation = useNavigation<ProfileScreenNavigationProp>();

  const renderPostItem = ({ item }: { item: { imageUrl: string } }) => (
    <Image source={{ uri: item.imageUrl }} style={styles.postImage} />
  );

  const handleMenuPress = () => {
    setShowMenu(true);
  };

  const handleMenuOption = (option: string) => {
    setShowMenu(false);
    if (option === 'Logout') {
      // Thực hiện logic đăng xuất ở đây (ví dụ: xóa token, clear state, ...)
      // Sau đó chuyển hướng đến màn hình Login
      navigation.navigate('Login');
    } else if (option === 'New Post') {
      navigation.navigate('CreatePost');
    }
    else {
      // Xử lý các tùy chọn khác
      console.log(`Selected option: ${option}`);
    }
  };

  return (
    <SafeAreaView style={styles.safeArea}>
      <View style={styles.container}>
        <View style={styles.statusBarPlaceholder} />
        <View style={styles.header}>
          <Text style={styles.headerTitle}>{userData.username}</Text>
          <TouchableOpacity onPress={handleMenuPress}>
            <Icon name="ellipsis-v" size={24} color="#000" />
          </TouchableOpacity>
        </View>

        <ScrollView>
          <View style={styles.profileInfo}>
            <Image source={{ uri: userData.avatar }} style={styles.avatar} />
            <View style={styles.stats}>
              <View style={styles.statItem}>
                <Text style={styles.statValue}>{userData.postsCount}</Text>
                <Text style={styles.statLabel}>Posts</Text>
              </View>
              <View style={styles.statItem}>
                <Text style={styles.statValue}>{userData.followersCount}</Text>
                <Text style={styles.statLabel}>Followers</Text>
              </View>
              <View style={styles.statItem}>
                <Text style={styles.statValue}>{userData.followingCount}</Text>
                <Text style={styles.statLabel}>Following</Text>
              </View>
            </View>
          </View>
          <View style={styles.bioSection}>
            <Text style={styles.name}>{userData.name}</Text>
            <Text style={styles.bio}>{userData.bio}</Text>
          </View>

          <FlatList
            data={postData}
            renderItem={renderPostItem}
            keyExtractor={(item) => item.id}
            numColumns={3}
            scrollEnabled={false}
          />
        </ScrollView>

        <Modal
          visible={showMenu}
          transparent={true}
          animationType="fade"
          onRequestClose={() => setShowMenu(false)}
        >
          <TouchableOpacity
            style={styles.modalOverlay}
            activeOpacity={1}
            onPress={() => setShowMenu(false)}
          >
            <View style={styles.menuContainer}>
              <TouchableOpacity
                style={styles.menuItem}
                onPress={() => handleMenuOption('Edit Profile')}
              >
                <Text>Edit Profile</Text>
              </TouchableOpacity>
              <TouchableOpacity
                style={styles.menuItem}
                onPress={() => handleMenuOption('New Post')}
              >
                <Text>Post bài viết mới</Text>
              </TouchableOpacity>
              <TouchableOpacity
                style={styles.menuItem}
                onPress={() => handleMenuOption('Logout')}
              >
                <Text>Đăng xuất</Text>
              </TouchableOpacity>
            </View>
          </TouchableOpacity>
        </Modal>
      </View>
    </SafeAreaView>
  );
};

const STATUSBAR_HEIGHT = Platform.OS === 'ios' ? 20 : StatusBar.currentHeight;

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: '#fff',
  },
  container: {
    flex: 1,
  },
  statusBarPlaceholder: {
    height: STATUSBAR_HEIGHT,
    backgroundColor: '#fff',
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: 16,
    borderBottomWidth: 1,
    borderBottomColor: '#dbdbdb',
    backgroundColor: '#fff',
  },
  headerTitle: {
    fontSize: 18,
    fontWeight: 'bold',
  },
  profileInfo: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 16,
  },
  avatar: {
    width: 80,
    height: 80,
    borderRadius: 40,
    marginRight: 16,
  },
  stats: {
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'space-around',
  },
  statItem: {
    alignItems: 'center',
  },
  statValue: {
    fontSize: 18,
    fontWeight: 'bold',
  },
  statLabel: {
    fontSize: 12,
    color: '#666',
  },
  bioSection: {
    paddingHorizontal: 16,
    marginBottom: 16,
  },
  name: {
    fontSize: 16,
    fontWeight: 'bold',
    marginBottom: 4,
  },
  bio: {
    fontSize: 14,
  },
  postImage: {
    width: width / 3 - 2,
    height: width / 3 - 2,
    margin: 1,
  },
  modalOverlay: {
    flex: 1,
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
    justifyContent: 'flex-end',
  },
  menuContainer: {
    backgroundColor: '#fff',
    borderTopLeftRadius: 20,
    borderTopRightRadius: 20,
    padding: 16,
  },
  menuItem: {
    paddingVertical: 12,
    borderBottomWidth: 1,
    borderBottomColor: '#dbdbdb',
  },
});

export default ProfileScreen;