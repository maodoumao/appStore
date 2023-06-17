
import random
import string
import os
# '''
# 脚本生成混淆字典
# '''

# //生产8000个不重复的字符串
totalNum = 8000


def main():
    dir = "./proguardbuild"
    createProRules(dir+'/pro_package.txt')
    createProRules(dir+'/pro_class.txt')
    createProRules(dir+'/pro_func.txt')


def createProRules(fileName):
    dirName = os.path.dirname(fileName)
    if not os.path.exists(dirName):
        os.mkdir(dirName)
    '''
    生成totalNum个随机不重复的字符串
    :return:
    '''
    new_list = []
    while 1:
        value = ''.join(random.sample(string.ascii_letters +
                        string.digits, random.randint(1, 8)))  #//生成1~8位数随机字符串
        if value not in new_list:
            new_list.append(value)
            if len(new_list) == totalNum:
                break
        else:
            continue
    result = '\n'.join(new_list)
    file = open(fileName, 'w', encoding='UTF-8')
    file.write(result)
    file.close()


if __name__ == '__main__':
    main()

