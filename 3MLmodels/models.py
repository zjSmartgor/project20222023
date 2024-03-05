import nn

class PerceptronModel(object):
    def __init__(self, dimensions):
        """
        Initialize a new Perceptron instance.

        A perceptron classifies data points as either belonging to a particular
        class (+1) or not (-1). `dimensions` is the dimensionality of the data.
        For example, dimensions=2 would mean that the perceptron must classify
        2D points.
        """
        self.w = nn.Parameter(1, dimensions)

    def get_weights(self):
        """
        Return a Parameter instance with the current weights of the perceptron.
        """
        return self.w

    def run(self, x):
        """
        Calculates the score assigned by the perceptron to a data point x.

        Inputs:
            x: a node with shape (1 x dimensions)
        Returns: a node containing a single number (the score)
        """
        "*** YOUR CODE HERE ***"
        return nn.DotProduct(self.get_weights(), x)

    def get_prediction(self, x):
        """
        Calculates the predicted class for a single data point `x`.

        Returns: 1 or -1
        """
        "*** YOUR CODE HERE ***"
        scaleValue = nn.as_scalar(self.run(x=x))
        if scaleValue >= 0:
            return 1
        else:
            return -1

    def train(self, dataset):
        """
        Train the perceptron until convergence.
        """
        "*** YOUR CODE HERE ***"
        mistake = True
        while mistake:
            mistakeCount =0
            for x, y in dataset.iterate_once(1):
                prediction = self.get_prediction(x)
                yScarleValue = nn.as_scalar(y)
                
                if prediction != yScarleValue :
                    #notice here we use true y label as mutiplier instead of prediction
                    self.get_weights().update(x, yScarleValue)
                    mistakeCount +=1
                print([prediction, yScarleValue, mistakeCount])
            if mistakeCount ==0:
                mistake = False
        
                



class RegressionModel(object):
    """
    A neural network model for approximating a function that maps from real
    numbers to real numbers. The network should be sufficiently large to be able
    to approximate sin(x) on the interval [-2pi, 2pi] to reasonable precision.
    """
    def __init__(self):
        # Initialize your model parameters here
        "*** YOUR CODE HERE ***"
        self.layerSize = 512
        self.batchSize = 200
        self.learningRate = 0.05
        self.w1 = nn.Parameter(1, self.layerSize)
        self.b1 = nn.Parameter(1, self.layerSize)
        self.w2 = nn.Parameter(self.layerSize, 1)
        self.b2 = nn.Parameter(1, 1)


    def run(self, x):
        """
        Runs the model for a batch of examples.

        Inputs:
            x: a node with shape (batch_size x 1)
        Returns:
            A node with shape (batch_size x 1) containing predicted y-values
        """
        "*** YOUR CODE HERE ***"
        z1 = nn.AddBias(nn.Linear(x, self.w1), self.b1)
        a1 = nn.ReLU(z1)
        
        # Second linear layer to get predictions
        yPred = nn.AddBias(nn.Linear(a1, self.w2), self.b2)
        return yPred

    def get_loss(self, x, y):
        """
        Computes the loss for a batch of examples.

        Inputs:
            x: a node with shape (batch_size x 1)
            y: a node with shape (batch_size x 1), containing the true y-values
                to be used for training
        Returns: a loss node
        """
        "*** YOUR CODE HERE ***"
        yPred = self.run(x)
        return nn.SquareLoss(yPred, y)

    def train(self, dataset):
        """
        Trains the model.
        """
        "*** YOUR CODE HERE ***"
        loss = float('inf')
        while loss > 0.018:
            for xSample, ySample in dataset.iterate_once(self.batchSize):
                lossClass = self.get_loss(xSample, ySample)
                loss = nn.as_scalar(lossClass)
                gradients = nn.gradients(lossClass, [self.w1, self.b1, self.w2, self.b2])
                self.w1.update(gradients[0], -self.learningRate)
                self.b1.update(gradients[1], -self.learningRate)
                self.w2.update(gradients[2], -self.learningRate)
                self.b2.update(gradients[3], -self.learningRate)
        

class DigitClassificationModel(object):
    """
    A model for handwritten digit classification using the MNIST dataset.

    Each handwritten digit is a 28x28 pixel grayscale image, which is flattened
    into a 784-dimensional vector for the purposes of this model. Each entry in
    the vector is a floating point number between 0 and 1.

    The goal is to sort each digit into one of 10 classes (number 0 through 9).

    (See RegressionModel for more information about the APIs of different
    methods here. We recommend that you implement the RegressionModel before
    working on this part of the project.)
    """
    def __init__(self):
        # Initialize your model parameters here
        "*** YOUR CODE HERE ***"
        self.layerSize = 200
        self.batchSize = 100
        self.learningRate = 0.5

        #first layer
        self.w1 = nn.Parameter(784, self.layerSize)
        self.b1 = nn.Parameter(1, self.layerSize)
        #second layer
        self.w2 = nn.Parameter(self.layerSize, 10)
        self.b2 = nn.Parameter(1, 10)

    def run(self, x):
        """
        Runs the model for a batch of examples.

        Your model should predict a node with shape (batch_size x 10),
        containing scores. Higher scores correspond to greater probability of
        the image belonging to a particular class.

        Inputs:
            x: a node with shape (batch_size x 784)
        Output:
            A node with shape (batch_size x 10) containing predicted scores
                (also called logits)
        """
        "*** YOUR CODE HERE ***"
        z1 = nn.AddBias(nn.Linear(x, self.w1), self.b1)
        a1 = nn.ReLU(z1)
        yPred = nn.AddBias(nn.Linear(a1, self.w2), self.b2)
        return yPred

    def get_loss(self, x, y):
        """
        Computes the loss for a batch of examples.

        The correct labels `y` are represented as a node with shape
        (batch_size x 10). Each row is a one-hot vector encoding the correct
        digit class (0-9).

        Inputs:
            x: a node with shape (batch_size x 784)
            y: a node with shape (batch_size x 10)
        Returns: a loss node
        """
        "*** YOUR CODE HERE ***"
        yPred = self.run(x)
        return nn.SoftmaxLoss(yPred, y )

    def train(self, dataset):
        """
        Trains the model.
        """
        "*** YOUR CODE HERE ***"
        #loss = float('inf')
        while True:
            for xSample, ySample in dataset.iterate_once(self.batchSize):
                lossClass = self.get_loss(xSample,ySample)
                # not necessary in this case b/c we just use validation accuracy to stop the loop
                #lossValue = nn.as_scalar(lossClass)
                gradients = nn.gradients(lossClass, [self.w1, self.b1, self.w2, self.b2])
                self.w1.update(gradients[0], -self.learningRate)
                self.b1.update(gradients[1], -self.learningRate)
                self.w2.update(gradients[2], -self.learningRate)
                self.b2.update(gradients[3], -self.learningRate)
            valiAccuracy = dataset.get_validation_accuracy()
            if valiAccuracy > 0.982:
                break


class LanguageIDModel(object):
    """
    A model for language identification at a single-word granularity.

    (See RegressionModel for more information about the APIs of different
    methods here. We recommend that you implement the RegressionModel before
    working on this part of the project.)
    """
    def __init__(self):
        # Our dataset contains words from five different languages, and the
        # combined alphabets of the five languages contain a total of 47 unique
        # characters.
        # You can refer to self.num_chars or len(self.languages) in your code
        self.num_chars = 47
        self.languages = ["English", "Spanish", "Finnish", "Dutch", "Polish"]

        # Initialize your model parameters here
        "*** YOUR CODE HERE ***"
        self.layerSize = 200  
        self.learningRate = 0.01
        self.batchSize = 100
        # the paras for initial status 
        self.wInit = nn.Parameter(self.num_chars,self.layerSize)
        self.bInit = nn.Parameter(1,self.layerSize)
        
        # paras for recursion status 
        self.wOfX = nn.Parameter(self.num_chars,self.layerSize)
        self.wHidden = nn.Parameter(self.layerSize,self.layerSize)
        self.bRecuresion = nn.Parameter(1,self.layerSize)
        
        # para for final status 
        self.wFinal = nn.Parameter(self.layerSize, 5) # 5 is length of self.language
        self.bFinal = nn.Parameter(1, len(self.languages))

    def run(self, xs):
        """
        Runs the model for a batch of examples.

        Although words have different lengths, our data processing guarantees
        that within a single batch, all words will be of the same length (L).

        Here `xs` will be a list of length L. Each element of `xs` will be a
        node with shape (batch_size x self.num_chars), where every row in the
        array is a one-hot vector encoding of a character. For example, if we
        have a batch of 8 three-letter words where the last word is "cat", then
        xs[1] will be a node that contains a 1 at position (7, 0). Here the
        index 7 reflects the fact that "cat" is the last word in the batch, and
        the index 0 reflects the fact that the letter "a" is the inital (0th)
        letter of our combined alphabet for this task.

        Your model should use a Recurrent Neural Network to summarize the list
        `xs` into a single node of shape (batch_size xself.layerSize), for your
        choice ofself.layerSize. It should then calculate a node of shape
        (batch_size x 5) containing scores, where higher scores correspond to
        greater probability of the word originating from a particular language.

        Inputs:
            xs: a list with L elements (one per character), where each element
                is a node with shape (batch_size x self.num_chars)
        Returns:
            A node with shape (batch_size x 5) containing predicted scores
                (also called logits)
        """
        "*** YOUR CODE HERE ***"
        # create the initial h 
        h = nn.ReLU(nn.AddBias(nn.Linear(xs[0], self.wInit), self.bInit))
        
        #combine all x feature with h
        for x in xs[1:]:
            zAndx = nn.Linear(x, self.wOfX)
            zAndh = nn.Linear(h, self.wHidden)
            combinedZ = nn.Add(zAndx, zAndh)
            h = nn.ReLU(combinedZ)
        
        return nn.AddBias(nn.Linear(h, self.wFinal), self.bFinal)



    def get_loss(self, xs, y):
        """
        Computes the loss for a batch of examples.

        The correct labels `y` are represented as a node with shape
        (batch_size x 5). Each row is a one-hot vector encoding the correct
        language.

        Inputs:
            xs: a list with L elements (one per character), where each element
                is a node with shape (batch_size x self.num_chars)
            y: a node with shape (batch_size x 5)
        Returns: a loss node
        """
        "*** YOUR CODE HERE ***"
        yPred = self.run(xs)
        return nn.SoftmaxLoss(yPred, y)

    def train(self, dataset):
        """
        Trains the model.
        """
        "*** YOUR CODE HERE ***"
        
        while True:
            for xsSample, ySample in dataset.iterate_once(self.batchSize):
                lossClass = self.get_loss(xsSample, ySample)

                gradients = nn.gradients(lossClass, [self.wInit, self.bInit, 
                                                self.wOfX, self.wHidden, self.bRecuresion,
                                                self.wFinal, self.bFinal])
                self.wInit.update(gradients[0], -self.learningRate)
                self.bInit.update(gradients[1], -self.learningRate)
                self.wOfX.update(gradients[2], -self.learningRate)
                self.wHidden.update(gradients[3], -self.learningRate)
                self.bRecuresion.update(gradients[4], -self.learningRate)
                self.wFinal.update(gradients[5], -self.learningRate)
                self.bFinal.update(gradients[6], -self.learningRate)
            valiAccuracy = dataset.get_validation_accuracy()
            if valiAccuracy > 0.88:
                break
