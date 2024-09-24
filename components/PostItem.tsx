// src/components/PostItem.tsx
import React from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity } from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome';
import { Post } from '../types/types'; // Import kiểu Post nếu bạn đặt trong file riêng
import LikeButton from '../components/LikeButton';

// Khai báo kiểu cho props của component
interface PostItemProps {
  post: Post; // Sử dụng kiểu Post đã khai báo
}

const PostItem: React.FC<PostItemProps> = ({ post }) => {
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
        <View >
        
        </View>
        <View >
            <TouchableOpacity style={styles.actions}>
                <Icon name="comment-o" size={24} style={styles.icon} />
                <Text >Comment </Text>
            </TouchableOpacity>
            
        </View>
        <View >
            <TouchableOpacity style={styles.actions}>
                <Icon name="send-o" size={24} style={styles.icon} />
                <Text >Share </Text>
            </TouchableOpacity>
            
        </View>
        
        
        
      </View>

      {/* Caption */}
      <View style={styles.caption}>
        <Text style={styles.username}>{post.username}</Text>
        <Text> {post.caption}</Text>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  postContainer: {
    marginBottom: 20,
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
  },
  actions: {
    justifyContent: 'space-between',
    flexDirection: 'row',
    padding: 10,
  },
  icon: {
    marginRight: 15,
  },
  caption: {
    flexDirection: 'row',
    paddingHorizontal: 10,
    paddingBottom: 10,
  },
});

export default PostItem;
