export interface Post {
    id: string;
    username: string;
    userAvatar: string;
    imageUrl: string;
    caption: string;
    createdAt: string;
    status: number; 
    commentCount: number;
    // ... các trường khác nếu có
  };