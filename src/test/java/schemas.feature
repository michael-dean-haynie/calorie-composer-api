Feature: Prepare schemas
	Scenario: Prepare schemas
		* def schemas = {}

		# ---------------------------------------------
		# Food and Nutrient
		# ---------------------------------------------

		* set schemas.nutrient =
		"""
		{
            id: '##number',
            name: '#string',
            unit: '#string',
            scalar: '#number'
		}
		"""

		* set schemas.portion =
		"""
		{
            id: '##number',
			isNutrientRefPortion: '#boolean',
            isServingSizePortion: '#boolean',
            metricUnit: '#string',
            metricScalar: '#number',
            householdMeasure: '##string',
            householdUnit: '##string',
            householdScalar: '##number'
		}
		"""

		* set schemas.food =
		"""
		{
			id: '##number',
			fdcId: '##string',
			description: '#string',
			brandOwner: '##string',
			ingredients: '##string',
			nutrients: '##[] schemas.nutrient',
			portions: '##[] schemas.portion'
		}
		"""

		# ---------------------------------------------
		# Combo Food
		# ---------------------------------------------

		* set schemas.comboFoodFoodAmount =
		"""
		{
			id: '##number',
			food: '#(schemas.food)',
			unit: '##string',
			scalar: '##number',
		}
		"""

		* set schemas.comboFoodPortion =
		"""
		{
			id: '##number',
			isFoodAmountRefPortion: '#boolean',
            isServingSizePortion: '#boolean',
			metricUnit: '##string',
            metricScalar: '##number',
            householdUnit: '##string',
            householdScalar: '##number'
		}
		"""

		* set schemas.comboFood =
		"""
		{
			id: '##number',
			isDraft: '#boolean',
			description: '##string',
			foodAmounts: '##[] schemas.comboFoodFoodAmount',
			portions: '##[] schemas.comboFoodPortion'
		}
		"""