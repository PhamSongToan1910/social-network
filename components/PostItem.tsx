import React from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity } from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome';
import { Post } from '../types/types'; // Đảm bảo bạn đã định nghĩa kiểu Post
import LikeButton from './LikeButton';
import ShareButton from './ShareButton';
import { NavigationProp, useNavigation } from '@react-navigation/native';

interface PostItemProps {
  post: Post;
}

const PostItem: React.FC<PostItemProps> = ({ post }) => {
  const navigation = useNavigation<NavigationProp<RootStackParamList>>();

  return (
    <View style={styles.postContainer}>
      {/* Header */}
      <View style={styles.header}>
        <Image source={{ uri: post.userAvatar }} style={styles.avatar} />
        <Text style={styles.username}>{post.username}</Text>
      </View>

      {/* Post Image */}
      <Image source={{ uri: post.imageUrl }} style={styles.postImage} />

      {/* Actions */}
      <View style={styles.actions}>
        <LikeButton />
        <TouchableOpacity style={styles.actionButton} >
          <Icon name="comment-o" size={24} style={styles.icon} />
          <Text style={styles.actionText}>Comment</Text>
        </TouchableOpacity>
        
        <ShareButton userAvatar={post.userAvatar} userName={post.username} />
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
  username: {
    fontWeight: 'bold',
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