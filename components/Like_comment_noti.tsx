import React from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity } from 'react-native';
import Icon from 'react-native-vector-icons/Ionicons';

interface Like_comment_notiProps {
  type: 'like' | 'comment';
  userAvatar: string;
  userName: string;
  time: string;
  postImage: string;
  isRead: boolean;
  onPress: () => void;
}

const Like_comment_noti: React.FC<Like_comment_notiProps> = ({
  type,
  userAvatar,
  userName,
  time,
  postImage,
  isRead,
  onPress,
}) => {
  return (
    <TouchableOpacity
      style={[styles.container, !isRead && styles.unreadContainer]}
      onPress={onPress}
    >
      <Image source={{ uri: userAvatar }} style={styles.avatar} />
      <View style={styles.contentContainer}>
        <View style={styles.textContainer}>
          <Text style={styles.userName}>{userName}</Text>
          <Text style={styles.actionText}>
            {type === 'like' ? 'đã thích bài viết của bạn' : 'đã bình luận về bài viết của bạn'}
          </Text>
          <Text style={styles.timeText}>{time}</Text>
        </View>
        <Image source={{ uri: postImage }} style={styles.postImage} />
      </View>
      <Icon name="ellipsis-vertical" size={20} color="#65676b" style={styles.moreIcon} />
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    padding: 12,
    alignItems: 'center',
    borderBottomWidth: 1,
    borderBottomColor: '#e0e0e0',
  },
  unreadContainer: {
    backgroundColor: '#e7f3ff',
  },
  avatar: {
    width: 50,
    height: 50,
    borderRadius: 25,
    marginRight: 12,
  },
  contentContainer: {
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  textContainer: {
    flex: 1,
    marginRight: 12,
  },
  userName: {
    fontWeight: 'bold',
    fontSize: 14,
    color: '#1c1e21',
  },
  actionText: {
    fontSize: 14,
    color: '#1c1e21',
  },
  timeText: {
    fontSize: 12,
    color: '#65676b',
    marginTop: 4,
  },
  postImage: {
    width: 40,
    height: 40,
    borderRadius: 4,
  },
  moreIcon: {
    marginLeft: 8,
  },
});

export default Like_comment_noti;