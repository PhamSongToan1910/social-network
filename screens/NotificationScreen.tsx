import React from 'react';
import { View, Text, FlatList, StyleSheet, TouchableOpacity, SafeAreaView, Platform, StatusBar } from 'react-native';
import Icon from 'react-native-vector-icons/Ionicons';
import AddFriend from '../components/AddFriend';
import Like_comment_noti from '../components/Like_comment_noti';

interface Notification {
  id: string;
  type: 'like' | 'comment' | 'friend_request';
  userAvatar: string;
  userName: string;
  time: string;
  isRead: boolean;
  // postImage?: string; // Xóa dòng này
}

interface FriendRequest {
  id: string;
  userAvatar: string;
  userName: string;
}

const mockNotifications: Notification[] = [
  {
    id: '1',
    type: 'like',
    userAvatar: 'https://example.com/avatar1.jpg',
    userName: 'Nguyễn Văn A',
    time: '5 phút trước',
    isRead: false,
    // postImage: 'https://example.com/post1.jpg', // Xóa dòng này
  },
  {
    id: '2',
    type: 'comment',
    userAvatar: 'https://example.com/avatar2.jpg',
    userName: 'Trần Thị B',
    time: '15 phút trước',
    isRead: true,
    // postImage: 'https://example.com/post2.jpg', // Xóa dòng này
  },
  {
    id: '3',
    type: 'friend_request',
    userAvatar: 'https://example.com/avatar3.jpg',
    userName: 'Lê Văn C',
    time: '1 giờ trước',
    isRead: false,
  },
];

const mockFriendRequests: FriendRequest[] = [
  {
    id: '4',
    userAvatar: 'https://example.com/avatar4.jpg',
    userName: 'Phạm Thị D',
  },
  {
    id: '5',
    userAvatar: 'https://example.com/avatar5.jpg',
    userName: 'Hoàng Văn E',
  },
];

const NotificationScreen = () => {
  const handleAcceptFriend = (id: string) => {
    console.log('Accepted friend request with id:', id);
    // Implement logic to accept friend request
  };

  const handleDeclineFriend = (id: string) => {
    console.log('Declined friend request with id:', id);
    // Implement logic to decline friend request
  };

  const handleNotificationPress = (id: string) => {
    console.log('Pressed notification with id:', id);
    // Implement logic to handle notification press
  };

  const renderItem = ({ item }: { item: Notification | FriendRequest }) => {
    if ('type' in item) {
      if (item.type === 'friend_request') {
        return (
          <AddFriend
            userAvatar={item.userAvatar}
            userName={item.userName}
            onAccept={() => handleAcceptFriend(item.id)}
            onDecline={() => handleDeclineFriend(item.id)}
          />
        );
      } else {
        return (
          <Like_comment_noti
            type={item.type}
            userAvatar={item.userAvatar}
            userName={item.userName}
            time={item.time}
            // postImage={item.postImage || ''} // Xóa dòng này
            isRead={item.isRead}
            onPress={() => handleNotificationPress(item.id)}
          />
        );
      }
    } else {
      return (
        <AddFriend
          userAvatar={item.userAvatar}
          userName={item.userName}
          onAccept={() => handleAcceptFriend(item.id)}
          onDecline={() => handleDeclineFriend(item.id)}
        />
      );
    }
  };

  return (
    <SafeAreaView style={styles.safeArea}>
      <View style={styles.container}>
        <View style={styles.header}>
          <Text style={styles.headerTitle}>Thông báo</Text>
          <TouchableOpacity>
            <Icon name="search" size={24} color="#000" />
          </TouchableOpacity>
        </View>
        <FlatList
          data={[...mockNotifications, ...mockFriendRequests]}
          renderItem={renderItem}
          keyExtractor={(item) => item.id}
        />
      </View>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: '#ffffff',
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
    borderBottomColor: '#e0e0e0',
    backgroundColor: '#ffffff',
  },
  headerTitle: {
    fontSize: 20,
    fontWeight: 'bold',
  },
  notificationItem: {
    flexDirection: 'row',
    padding: 15,
    borderBottomWidth: 1,
    borderBottomColor: '#e0e0e0',
  },
  unreadItem: {
    backgroundColor: '#e7f3ff',
  },
  avatar: {
    width: 50,
    height: 50,
    borderRadius: 25,
    marginRight: 15,
  },
  notificationContent: {
    flex: 1,
  },
  message: {
    fontSize: 16,
    color: '#1c1e21',
  },
  time: {
    fontSize: 14,
    color: '#65676b',
    marginTop: 5,
  },
  moreIcon: {
    alignSelf: 'center',
  },
});

export default NotificationScreen;