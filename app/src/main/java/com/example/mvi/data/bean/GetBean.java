package com.example.mvi.data.bean;

public class GetBean  {
        private int courseId;
        private int id;
        private String name;
        private int order;
        private int parentChapterId;
        private boolean userControlSetTop;
        private int visible;

        public int getCourseId() {
            return courseId;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getOrder() {
            return order;
        }

        public int getParentChapterId() {
            return parentChapterId;
        }

        public boolean isUserControlSetTop() {
            return userControlSetTop;
        }

        public int getVisible() {
            return visible;
        }
}
