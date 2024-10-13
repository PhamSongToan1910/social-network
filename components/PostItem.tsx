import React from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity } from 'react-native';
import MaterialIcon from 'react-native-vector-icons/MaterialIcons';
import { Post } from '../types/types'; // Đảm bảo bạn đã định nghĩa kiểu Post
import LikeButton from './LikeButton';
import ShareButton from './ShareButton';
import CommentButton from './CommentButton';
import WebView from 'react-native-webview';

interface PostItemProps {
  post: Post;
}

const PostItem: React.FC<PostItemProps> = ({ post }) => {
  const getStatusIcon = (status: Number) => {
    switch (status) {
      case 1:
        return <MaterialIcon name="public" size={16} color="#888" />;
      case 2:
        return <MaterialIcon name="people" size={16} color="#888" />;
      case 3:
        return <MaterialIcon name="lock" size={16} color="#888" />;
      default:
        return null;
    }
  };
  
  return (
    <View style={styles.postContainer}>
      {/* Header */}
      <View style={styles.header}>
        <Image source={{ uri: post.image }} style={styles.avatar} />
        <View style={styles.headerTextContainer}>
          <Text style={styles.userID}>{post.userID}</Text>
          <View style={styles.timeStatusContainer}>
            <Text style={styles.timeText}>{post.createdAt}</Text>
            {getStatusIcon(post.status)}
          </View>
        </View>
      </View>

      {/* Post Image */}
      <Image source={{ uri: post.image }} style={styles.postImage} />

      {/* Actions */}
      <View style={styles.actions}>
        <LikeButton />
        <CommentButton postId={post.id} commentCount={post.commentCount} />
        <ShareButton
          userAvatar={post.userAvatar}
          userName={post.username}
          postId={post.id} 
        />
      </View>

      {/* Caption */}
      <View style={styles.caption}>
        <Text style={styles.username}>{post.username}</Text>
        <Text style={styles.captionText}>{post.caption}</Text>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  postContainer: {
    backgroundColor: 'white',
    marginBottom: 10,
    borderRadius: 10,
    overflow: 'hidden',
  },
  header: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 10,
  },
  avatar: {
    width: 40,
    height: 40,
    borderRadius: 20,
    marginRight: 10,
  },
  headerTextContainer: {
    flex: 1,
  },
  username: {
    fontWeight: 'bold',
  },
  timeStatusContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    marginTop: 2,
  },
  timeText: {
    fontSize: 12,
    color: '#888',
    marginRight: 8,
  },
  statusContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  statusText: {
    fontSize: 12,
    color: '#888',
    marginLeft: 4,
  },
  postImage: {
    width: '100%',
    height: 300,
    resizeMode: 'cover',
  },
  actions: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    padding: 10,
  },
  actionButton: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  icon: {
    marginRight: 5,
  },
  actionText: {
    fontSize: 14,
  },
  caption: {
    flexDirection: 'row',
    padding: 10,
  },
  captionText: {
    marginLeft: 5,
  },
});

export default PostItem;